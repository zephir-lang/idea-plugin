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
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.zephir.lang.core.completion.ZephirCompletionSuggestor
import com.zephir.lang.core.psi.ZephirClassDefinition
import com.zephir.lang.core.psi.ZephirFile
import com.zephir.lang.core.psi.ZephirInterfaceDefinition

object ZephirClassNameSuggestor : ZephirCompletionSuggestor {

    override fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet) {
        val project = parameters.position.project
        val basePath = project.basePath ?: return
        val baseDir = LocalFileSystem.getInstance().findFileByPath(basePath) ?: return
        val psiManager = PsiManager.getInstance(project)

        collectNames(baseDir, psiManager, result)
    }

    private fun collectNames(dir: VirtualFile, psiManager: PsiManager, result: CompletionResultSet) {
        for (child in dir.children) {
            if (child.isDirectory) {
                collectNames(child, psiManager, result)
            } else if (child.extension == "zep") {
                val psiFile = psiManager.findFile(child) as? ZephirFile ?: continue
                for (element in psiFile.children) {
                    when (element) {
                        is ZephirClassDefinition ->
                            result.addElement(LookupElementBuilder.create(element.id.text).withTypeText("class"))
                        is ZephirInterfaceDefinition ->
                            result.addElement(LookupElementBuilder.create(element.id.text).withTypeText("interface"))
                    }
                }
            }
        }
    }
}
