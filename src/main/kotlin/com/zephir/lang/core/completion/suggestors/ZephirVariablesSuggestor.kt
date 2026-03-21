// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion.suggestors

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.lang.core.completion.ZephirCompletionPriority
import com.zephir.lang.core.completion.ZephirCompletionSuggestor
import com.zephir.lang.core.psi.ZephirAssignmentExpr
import com.zephir.lang.core.psi.ZephirDeclarationStatement
import com.zephir.lang.core.psi.ZephirForeachLoopStatement
import com.zephir.lang.core.psi.ZephirMethodBody

object ZephirVariablesSuggestor : ZephirCompletionSuggestor {

    override fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet) {
        val psiElement = parameters.originalPosition ?: return
        val methodBody = PsiTreeUtil.getParentOfType(psiElement, ZephirMethodBody::class.java) ?: return

        val seen = mutableSetOf<String>()

        // let x = expr — only the LHS assignment target, not variables in the RHS expression
        for (assignExpr in PsiTreeUtil.findChildrenOfType(methodBody, ZephirAssignmentExpr::class.java)) {
            val idList = assignExpr.variable?.complexId?.idList ?: continue
            if (idList.size == 1) {
                val name = idList.first().text
                if (seen.add(name)) addVariable(name, result)
            }
        }

        // var x, string y, int z (type-annotated local declarations)
        for (declStmt in PsiTreeUtil.findChildrenOfType(methodBody, ZephirDeclarationStatement::class.java)) {
            for (id in declStmt.idList) {
                val name = id.text
                if (seen.add(name)) addVariable(name, result)
            }
        }

        // for key, value in ...
        for (forStmt in PsiTreeUtil.findChildrenOfType(methodBody, ZephirForeachLoopStatement::class.java)) {
            for (id in forStmt.idList) {
                val name = id.text
                if (seen.add(name)) addVariable(name, result)
            }
        }
    }

    private fun addVariable(name: String, result: CompletionResultSet) {
        result.addElement(
            PrioritizedLookupElement.withPriority(
                LookupElementBuilder.create(name).withBoldness(true),
                ZephirCompletionPriority.METHOD_SCOPE_PRIORITY
            )
        )
    }
}
