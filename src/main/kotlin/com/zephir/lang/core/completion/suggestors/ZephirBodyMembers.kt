// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion.suggestors

import com.zephir.lang.core.psi.ZephirConstantDefinition
import com.zephir.lang.core.psi.ZephirMethodDefinition
import com.zephir.lang.core.psi.ZephirPropertyDefinition

/** The constants, properties and methods of a class body or a trait body, which share their members' shape. */
internal class ZephirBodyMembers(
    val constants: List<ZephirConstantDefinition>,
    val properties: List<ZephirPropertyDefinition>,
    val methods: List<ZephirMethodDefinition>
)
