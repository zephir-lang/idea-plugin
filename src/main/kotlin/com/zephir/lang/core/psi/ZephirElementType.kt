// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.psi

import com.intellij.psi.tree.IElementType
import com.zephir.lang.core.ZephirLanguage

/** The type of intermediate PSI tree nodes */
class ZephirElementType(debugName: String) : IElementType(debugName, ZephirLanguage)
