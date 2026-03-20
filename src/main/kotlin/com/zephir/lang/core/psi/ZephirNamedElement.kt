// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.psi

import com.intellij.psi.PsiNameIdentifierOwner

/** Marker interface for Zephir PSI elements that have a name identifier (classes, interfaces, methods, properties, constants). */
interface ZephirNamedElement : PsiNameIdentifierOwner
