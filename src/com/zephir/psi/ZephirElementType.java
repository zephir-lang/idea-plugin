// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

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
