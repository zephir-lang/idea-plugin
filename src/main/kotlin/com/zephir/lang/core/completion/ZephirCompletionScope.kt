// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion

import com.intellij.psi.PsiElement
import com.zephir.lang.core.psi.ZephirClassBody
import com.zephir.lang.core.psi.ZephirClassDefinition
import com.zephir.lang.core.psi.ZephirCodeBlock
import com.zephir.lang.core.psi.ZephirFile
import com.zephir.lang.core.psi.ZephirFunctionDefinition
import com.zephir.lang.core.psi.ZephirInterfaceBody
import com.zephir.lang.core.psi.ZephirInterfaceDefinition
import com.zephir.lang.core.psi.ZephirMethodDefinition
import com.zephir.lang.core.psi.ZephirTraitBody
import com.zephir.lang.core.psi.ZephirTraitDefinition

/** The kind of place the completion caret sits in. */
enum class ZephirCompletionScope {
    FILE, HEADER, CLASS, INTERFACE, CODE, OTHER;

    companion object {
        /**
         * The scope of [position]: the nearest enclosing file, class or trait body, interface body or code block.
         * The header of a class, interface or trait, between its keyword and its body, is [HEADER]; the rest of a
         * declaration's header, its name and its parameters are [OTHER]. Error elements are looked through.
         */
        fun of(position: PsiElement?): ZephirCompletionScope {
            var element = position?.parent
            while (element != null) {
                when (element) {
                    is ZephirFile -> return FILE
                    is ZephirClassBody, is ZephirTraitBody -> return CLASS
                    is ZephirInterfaceBody -> return INTERFACE
                    is ZephirCodeBlock -> return CODE
                    is ZephirClassDefinition, is ZephirInterfaceDefinition, is ZephirTraitDefinition -> return HEADER
                    else -> if (isDeclaration(element)) return OTHER
                }
                element = element.parent
            }
            return OTHER
        }

        private fun isDeclaration(element: PsiElement) =
            element is ZephirFunctionDefinition ||
                element is ZephirMethodDefinition
    }
}
