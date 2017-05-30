/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * This source file is subject the MIT license, that is bundled with
 * this package in the file LICENSE, and is available through the
 * world-wide-web at the following url:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

package com.zephir.psi;

import com.intellij.psi.tree.IElementType;
import com.zephir.ZephirLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ZephirTokenType extends IElementType {

    public ZephirTokenType(@NotNull @NonNls String debugName) {
        super(debugName, ZephirLanguage.INSTANCE);
    }
}
