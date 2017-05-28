/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * Licensed under the MIT License (MIT);
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

package com.zephir.psi;

import com.intellij.psi.tree.IElementType;
import com.zephir.ZephirLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ZephirElementType extends IElementType {
    public ZephirElementType(@NotNull @NonNls String debugName) {
        super(debugName, ZephirLanguage.INSTANCE);
    }
}
