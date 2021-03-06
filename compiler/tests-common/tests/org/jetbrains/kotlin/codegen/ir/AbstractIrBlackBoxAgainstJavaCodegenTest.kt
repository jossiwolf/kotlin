/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen.ir

import org.jetbrains.kotlin.codegen.AbstractBlackBoxAgainstJavaCodegenTest
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.test.TargetBackend

abstract class AbstractIrBlackBoxAgainstJavaCodegenTest : AbstractBlackBoxAgainstJavaCodegenTest() {
    override fun updateConfiguration(configuration: CompilerConfiguration) {
        super.updateConfiguration(configuration)
        configuration.put(JVMConfigurationKeys.IR, true)
    }

    override fun getBackend() = TargetBackend.JVM_IR
}
