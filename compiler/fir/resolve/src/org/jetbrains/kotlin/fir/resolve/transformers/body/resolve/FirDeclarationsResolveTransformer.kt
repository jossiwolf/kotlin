/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve

import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.copy
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor
import org.jetbrains.kotlin.fir.declarations.impl.FirValueParameterImpl
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind
import org.jetbrains.kotlin.fir.diagnostics.FirSimpleDiagnostic
import org.jetbrains.kotlin.fir.expressions.FirStatement
import org.jetbrains.kotlin.fir.resolve.ResolutionMode
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitDispatchReceiverValue
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitExtensionReceiverValue
import org.jetbrains.kotlin.fir.resolve.calls.extractLambdaInfoFromFunctionalType
import org.jetbrains.kotlin.fir.resolve.constructFunctionalTypeRef
import org.jetbrains.kotlin.fir.resolve.defaultType
import org.jetbrains.kotlin.fir.resolve.transformers.ControlFlowGraphReferenceTransformer
import org.jetbrains.kotlin.fir.resolve.transformers.StoreType
import org.jetbrains.kotlin.fir.resolve.withExpectedType
import org.jetbrains.kotlin.fir.resolvedTypeFromPrototype
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol
import org.jetbrains.kotlin.fir.types.*
import org.jetbrains.kotlin.fir.types.impl.FirComputingImplicitTypeRef
import org.jetbrains.kotlin.fir.types.impl.FirErrorTypeRefImpl
import org.jetbrains.kotlin.fir.types.impl.FirResolvedTypeRefImpl
import org.jetbrains.kotlin.fir.visitors.CompositeTransformResult
import org.jetbrains.kotlin.fir.visitors.FirTransformer
import org.jetbrains.kotlin.fir.visitors.compose
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.utils.addIfNotNull

class FirDeclarationsResolveTransformer(transformer: FirBodyResolveTransformer) : FirPartialBodyResolveTransformer(transformer) {
    private var primaryConstructorParametersScope: FirLocalScope? = null

    override fun transformDeclaration(declaration: FirDeclaration, data: ResolutionMode): CompositeTransformResult<FirDeclaration> {
        return components.withContainer(declaration) {
            declaration.replaceResolvePhase(transformerPhase)
            transformer.transformElement(declaration, data)
        }
    }

    override fun transformProperty(property: FirProperty, data: ResolutionMode): CompositeTransformResult<FirDeclaration> {
        if (property.isLocal) return transformLocalVariable(property, data)
        val returnTypeRef = property.returnTypeRef
        if (returnTypeRef !is FirImplicitTypeRef && implicitTypeOnly) return property.compose()
        if (property.resolvePhase == transformerPhase) return property.compose()
        dataFlowAnalyzer.enterProperty(property)
        if (returnTypeRef is FirImplicitTypeRef) {
            property.transformReturnTypeRef(StoreType, FirComputingImplicitTypeRef)
        }
        return withFullBodyResolve {
            withScopeCleanup(localScopes) {
                localScopes.addIfNotNull(primaryConstructorParametersScope)
                components.withContainer(property) {
                    property.transformChildrenWithoutAccessors(returnTypeRef)
                    if (property.initializer != null) {
                        storeVariableReturnType(property)
                    }
                    withScopeCleanup(localScopes) {
                        localScopes.add(FirLocalScope().apply {
                            storeBackingField(property)
                        })
                        property.transformAccessors()
                    }
                }
                property.replaceResolvePhase(transformerPhase)
                val controlFlowGraph = dataFlowAnalyzer.exitProperty(property)
                property.transformControlFlowGraphReference(ControlFlowGraphReferenceTransformer, controlFlowGraph)
                property.compose()
            }
        }
    }

    private fun transformLocalVariable(variable: FirProperty, data: ResolutionMode): CompositeTransformResult<FirDeclaration> {
        assert(variable.isLocal)
        variable.transformOtherChildren(transformer, withExpectedType(variable.returnTypeRef))
        if (variable.initializer != null) {
            storeVariableReturnType(variable)
        }
        variable.transformAccessors()
        localScopes.lastOrNull()?.storeDeclaration(variable)
        variable.replaceResolvePhase(transformerPhase)
        dataFlowAnalyzer.exitVariableDeclaration(variable)
        return variable.compose()
    }

    private fun FirProperty.transformChildrenWithoutAccessors(returnTypeRef: FirTypeRef): FirProperty {
        val data = withExpectedType(returnTypeRef)
        return transformReturnTypeRef(transformer, data).transformOtherChildren(transformer, data)
    }

    private fun <F : FirVariable<F>> FirVariable<F>.transformAccessors() {
        var enhancedTypeRef = returnTypeRef
        getter?.let {
            transformAccessor(it, enhancedTypeRef, this)
        }
        if (returnTypeRef is FirImplicitTypeRef) {
            storeVariableReturnType(this)
            enhancedTypeRef = returnTypeRef
        }
        setter?.let {
            it.valueParameters[0].transformReturnTypeRef(StoreType, enhancedTypeRef)
            transformAccessor(it, enhancedTypeRef, this)
        }
    }

    private fun transformAccessor(
        accessor: FirPropertyAccessor,
        enhancedTypeRef: FirTypeRef,
        owner: FirVariable<*>
    ) {
        if (accessor is FirDefaultPropertyAccessor || accessor.body == null) {
            transformFunction(accessor, withExpectedType(enhancedTypeRef))
        }
        val returnTypeRef = accessor.returnTypeRef
        if (returnTypeRef is FirImplicitTypeRef && enhancedTypeRef !is FirResolvedTypeRef) {
            accessor.transformReturnTypeRef(StoreType, FirComputingImplicitTypeRef)
        }
        val expectedReturnTypeRef = if (enhancedTypeRef is FirResolvedTypeRef && returnTypeRef !is FirResolvedTypeRef) {
            enhancedTypeRef
        } else {
            returnTypeRef
        }
        val receiverTypeRef = owner.receiverTypeRef
        if (receiverTypeRef != null) {
            withLabelAndReceiverType(owner.name, owner, receiverTypeRef.coneTypeUnsafe()) {
                transformFunctionWithGivenSignature(accessor, expectedReturnTypeRef)
            }
        } else {
            transformFunctionWithGivenSignature(accessor, expectedReturnTypeRef)
        } as CompositeTransformResult<FirStatement>
    }

    override fun transformRegularClass(regularClass: FirRegularClass, data: ResolutionMode): CompositeTransformResult<FirStatement> {
        localScopes.lastOrNull()?.storeDeclaration(regularClass)
        val oldConstructorScope = primaryConstructorParametersScope
        primaryConstructorParametersScope = null
        val type = regularClass.defaultType()
        val result = withLabelAndReceiverType(regularClass.name, regularClass, type) {
            val constructor = regularClass.declarations.firstOrNull() as? FirConstructor
            if (constructor?.isPrimary == true) {
                primaryConstructorParametersScope = FirLocalScope().apply {
                    constructor.valueParameters.forEach { this.storeDeclaration(it) }
                }
            }
            transformDeclaration(regularClass, data)
        }
        primaryConstructorParametersScope = oldConstructorScope
        return result as CompositeTransformResult<FirStatement>
    }

    override fun transformAnonymousObject(anonymousObject: FirAnonymousObject, data: ResolutionMode): CompositeTransformResult<FirStatement> {
        val type = anonymousObject.defaultType()
        anonymousObject.resultType = FirResolvedTypeRefImpl(anonymousObject.source, type)
        val result = withLabelAndReceiverType(null, anonymousObject, type) {
            transformDeclaration(anonymousObject, data)
        }
        return result as CompositeTransformResult<FirStatement>
    }

    private fun transformAnonymousFunctionWithLambdaResolution(
        anonymousFunction: FirAnonymousFunction, lambdaResolution: ResolutionMode.LambdaResolution
    ): FirAnonymousFunction {
        val receiverTypeRef = anonymousFunction.receiverTypeRef
        fun transform(): FirAnonymousFunction {
            val expectedReturnType =
                lambdaResolution.expectedReturnTypeRef ?: anonymousFunction.returnTypeRef.takeUnless { it is FirImplicitTypeRef }
            val result = transformFunction(anonymousFunction, withExpectedType(expectedReturnType)).single as FirAnonymousFunction
            val body = result.body
            return if (result.returnTypeRef is FirImplicitTypeRef && body != null) {
                result.transformReturnTypeRef(transformer, withExpectedType(body.resultType))
                result
            } else {
                result
            }
        }

        val label = anonymousFunction.label
        return if (label != null && receiverTypeRef != null) {
            withLabelAndReceiverType(Name.identifier(label.name), anonymousFunction, receiverTypeRef.coneTypeUnsafe()) {
                transform()
            }
        } else {
            transform()
        }
    }

    override fun transformSimpleFunction(simpleFunction: FirSimpleFunction, data: ResolutionMode): CompositeTransformResult<FirDeclaration> {
        val returnTypeRef = simpleFunction.returnTypeRef
        if ((returnTypeRef !is FirImplicitTypeRef) && implicitTypeOnly) {
            return simpleFunction.compose()
        }
        return withFullBodyResolve {
            if (returnTypeRef is FirImplicitTypeRef) {
                simpleFunction.transformReturnTypeRef(StoreType, FirComputingImplicitTypeRef)
            }

            val receiverTypeRef = simpleFunction.receiverTypeRef
            if (receiverTypeRef != null) {
                withLabelAndReceiverType(simpleFunction.name, simpleFunction, receiverTypeRef.coneTypeUnsafe()) {
                    transformFunctionWithGivenSignature(simpleFunction, returnTypeRef)
                }
            } else {
                transformFunctionWithGivenSignature(simpleFunction, returnTypeRef)
            }
        }
    }

    private fun transformFunctionWithGivenSignature(
        function: FirFunction<*>,
        returnTypeRef: FirTypeRef
    ): CompositeTransformResult<FirDeclaration> {
        if (function is FirSimpleFunction) {
            localScopes.lastOrNull()?.storeDeclaration(function)
        }
        val result = transformFunction(function, withExpectedType(returnTypeRef)).single as FirFunction<*>
        val body = result.body
        return if (result.returnTypeRef is FirImplicitTypeRef && body != null) {
            result.transformReturnTypeRef(transformer, withExpectedType(body.resultType))
            result
        } else {
            result
        }.compose()
    }

    override fun <F : FirFunction<F>> transformFunction(function: FirFunction<F>, data: ResolutionMode): CompositeTransformResult<FirStatement> {
        return withScopeCleanup(localScopes) {
            localScopes += FirLocalScope()
            dataFlowAnalyzer.enterFunction(function)
            transformDeclaration(function, data).also {
                val result = it.single as FirFunction<*>
                dataFlowAnalyzer.exitFunction(result)?.let { controlFlowGraph ->
                    result.transformControlFlowGraphReference(ControlFlowGraphReferenceTransformer, controlFlowGraph)
                }
            } as CompositeTransformResult<FirStatement>
        }
    }

    override fun transformConstructor(constructor: FirConstructor, data: ResolutionMode): CompositeTransformResult<FirDeclaration> {
        if (implicitTypeOnly) return constructor.compose()
        return transformFunction(constructor, data) as CompositeTransformResult<FirDeclaration>
    }

    override fun transformAnonymousInitializer(
        anonymousInitializer: FirAnonymousInitializer,
        data: ResolutionMode
    ): CompositeTransformResult<FirDeclaration> {
        if (implicitTypeOnly) return anonymousInitializer.compose()
        return withScopeCleanup(localScopes) {
            dataFlowAnalyzer.enterInitBlock(anonymousInitializer)
            localScopes.addIfNotNull(primaryConstructorParametersScope)
            transformDeclaration(anonymousInitializer, data).also {
                dataFlowAnalyzer.exitInitBlock(it.single as FirAnonymousInitializer)
            }
        }
    }

    override fun transformValueParameter(valueParameter: FirValueParameter, data: ResolutionMode): CompositeTransformResult<FirStatement> {
        localScopes.lastOrNull()?.storeDeclaration(valueParameter)
        if (valueParameter.returnTypeRef is FirImplicitTypeRef) {
            valueParameter.replaceResolvePhase(transformerPhase)
            return valueParameter.compose() // TODO
        }
        return (transformDeclaration(valueParameter, withExpectedType(valueParameter.returnTypeRef)).single as FirStatement).compose()
    }

    override fun transformAnonymousFunction(anonymousFunction: FirAnonymousFunction, data: ResolutionMode): CompositeTransformResult<FirStatement> {
        return when (data) {
            ResolutionMode.ContextDependent -> {
                anonymousFunction.compose()
            }
            is ResolutionMode.LambdaResolution -> {
                transformAnonymousFunctionWithLambdaResolution(anonymousFunction, data).compose()
            }
            is ResolutionMode.WithExpectedType -> {
                val resolvedLambdaAtom = (data.expectedTypeRef as? FirResolvedTypeRef)?.let {
                    extractLambdaInfoFromFunctionalType(
                        it.type, it, anonymousFunction
                    )
                }
                var af = anonymousFunction
                val valueParameters =
                    if (resolvedLambdaAtom == null) af.valueParameters
                    else {
                        val singleParameterType = resolvedLambdaAtom.parameters.singleOrNull()
                        val itParam = when {
                            af.valueParameters.isEmpty() && singleParameterType != null -> {
                                val name = Name.identifier("it")
                                FirValueParameterImpl(
                                    null,
                                    session,
                                    FirResolvedTypeRefImpl(null, singleParameterType),
                                    name,
                                    FirVariableSymbol(name),
                                    defaultValue = null,
                                    isCrossinline = false,
                                    isNoinline = false,
                                    isVararg = false
                                )
                            }
                            else -> null
                        }
                        if (itParam != null) {
                            listOf(itParam)
                        } else {
                            af.valueParameters.mapIndexed { index, param ->
                                if (param.returnTypeRef is FirResolvedTypeRef) {
                                    param
                                } else {
                                    param.transformReturnTypeRef(
                                        StoreType,
                                        param.returnTypeRef.resolvedTypeFromPrototype(
                                            resolvedLambdaAtom.parameters[index]
                                        )
                                    )
                                    param
                                }
                            }
                        }

                    }
                val returnTypeRefFromResolvedAtom = resolvedLambdaAtom?.returnType?.let { af.returnTypeRef.resolvedTypeFromPrototype(it) }
                af = af.copy(
                    receiverTypeRef = af.receiverTypeRef?.takeIf { it !is FirImplicitTypeRef }
                        ?: resolvedLambdaAtom?.receiver?.let { af.receiverTypeRef?.resolvedTypeFromPrototype(it) },
                    valueParameters = valueParameters,
                    returnTypeRef = (af.returnTypeRef as? FirResolvedTypeRef)
                        ?: returnTypeRefFromResolvedAtom
                        ?: af.returnTypeRef
                )
                af = af.transformValueParameters(ImplicitToErrorTypeTransformer, null)
                val bodyExpectedType = returnTypeRefFromResolvedAtom ?: data.expectedTypeRef
                af = transformFunction(af, withExpectedType(bodyExpectedType)).single as FirAnonymousFunction
                af = af.copy(
                    returnTypeRef = af.body?.resultType ?: FirErrorTypeRefImpl(af.source, FirSimpleDiagnostic("No result type for lambda", DiagnosticKind.InferenceError))
                )
                af.replaceTypeRef(af.constructFunctionalTypeRef(session))
                af.compose()
            }
            is ResolutionMode.ContextIndependent -> {
                transformFunction(anonymousFunction, data).single.compose()
            }
        }
    }

    private inline fun <T> withLabelAndReceiverType(
        labelName: Name?,
        owner: FirDeclaration,
        type: ConeKotlinType,
        block: () -> T
    ): T {
        val implicitReceiverValue = when (owner) {
            is FirClass<*> -> {
                ImplicitDispatchReceiverValue(owner.symbol, type, session, scopeSession)
            }
            is FirFunction<*> -> {
                ImplicitExtensionReceiverValue(owner.symbol, type, session, scopeSession)
            }
            is FirVariable<*> -> {
                ImplicitExtensionReceiverValue(owner.symbol, type, session, scopeSession)
            }
            else -> {
                throw IllegalArgumentException("Incorrect label & receiver owner: ${owner.javaClass}")
            }
        }
        implicitReceiverStack.add(labelName, implicitReceiverValue)
        val result = block()
        implicitReceiverStack.pop(labelName)
        return result
    }

    private fun storeVariableReturnType(variable: FirVariable<*>) {
        val initializer = variable.initializer
        if (variable.returnTypeRef is FirImplicitTypeRef) {
            when {
                initializer != null -> {
                    val expectedType = when (val resultType = initializer.resultType) {
                        is FirImplicitTypeRef -> FirErrorTypeRefImpl(
                            null,
                            FirSimpleDiagnostic("No result type for initializer", DiagnosticKind.InferenceError)
                        )
                        else -> resultType
                    }
                    variable.transformReturnTypeRef(
                        transformer,
                        withExpectedType(expectedType)
                    )
                }
                variable.getter != null && variable.getter !is FirDefaultPropertyAccessor -> {
                    val expectedType = when (val resultType = variable.getter?.returnTypeRef) {
                        is FirImplicitTypeRef -> FirErrorTypeRefImpl(
                            null,
                            FirSimpleDiagnostic("No result type for getter", DiagnosticKind.InferenceError)
                        )
                        else -> resultType
                    }
                    variable.transformReturnTypeRef(
                        transformer,
                        withExpectedType(expectedType)
                    )
                }
                else -> {
                    variable.transformReturnTypeRef(
                        transformer, withExpectedType(FirErrorTypeRefImpl(null, FirSimpleDiagnostic("Cannot infer variable type without initializer / getter / delegate", DiagnosticKind.InferenceError)))
                    )
                }
            }
            if (variable.getter?.returnTypeRef is FirImplicitTypeRef) {
                variable.getter?.transformReturnTypeRef(transformer, withExpectedType(variable.returnTypeRef))
            }
        }
    }

    private object ImplicitToErrorTypeTransformer : FirTransformer<Nothing?>() {
        override fun <E : FirElement> transformElement(element: E, data: Nothing?): CompositeTransformResult<E> {
            return element.compose()
        }

        override fun transformValueParameter(valueParameter: FirValueParameter, data: Nothing?): CompositeTransformResult<FirStatement> {
            if (valueParameter.returnTypeRef is FirImplicitTypeRef) {
                valueParameter.transformReturnTypeRef(
                    StoreType,
                    valueParameter.returnTypeRef.resolvedTypeFromPrototype(ConeKotlinErrorType("No type for parameter"))
                )
            }
            return valueParameter.compose()
        }
    }
}