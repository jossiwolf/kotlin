/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.calls.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.ValueArgument;

import java.util.ArrayList;
import java.util.List;

public class DelegationValueArgument implements ResolvedValueArgument {

    private ValueParameterDescriptor parameterDescriptor;

    public DelegationValueArgument(ValueParameterDescriptor parameterDescriptor) {
        this.parameterDescriptor = parameterDescriptor;
    }

    public ValueParameterDescriptor getParameterDescriptor() {
        return parameterDescriptor;
    }

    @NotNull
    @Override
    public List<ValueArgument> getArguments() {
        return new ArrayList<>();
    }
}