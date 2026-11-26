// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.psi

private val VISIBILITIES = setOf("public", "protected", "private")

/** The visibility keyword among the property's modifiers, lower-cased, or an empty string when there is none. */
fun ZephirPropertyDefinition.visibilityText(): String =
    memberModifiers.memberModifierList
        .map { it.text.lowercase() }
        .firstOrNull { it in VISIBILITIES } ?: ""
