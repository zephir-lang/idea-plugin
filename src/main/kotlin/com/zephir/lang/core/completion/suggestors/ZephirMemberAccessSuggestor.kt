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
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.lang.core.completion.ZephirCompletionSuggestor
import com.zephir.lang.core.psi.ZephirAbstractMethodDefinition
import com.zephir.lang.core.psi.ZephirClassBody
import com.zephir.lang.core.psi.ZephirClassDefinition
import com.zephir.lang.core.psi.ZephirDeclarationStatement
import com.zephir.lang.core.psi.ZephirFile
import com.zephir.lang.core.psi.ZephirInterfaceBody
import com.zephir.lang.core.psi.ZephirInterfaceDefinition
import com.zephir.lang.core.psi.ZephirMethodDefinition
import com.zephir.lang.core.psi.ZephirVariable

object ZephirMemberAccessSuggestor : ZephirCompletionSuggestor {

    override fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet) {
        // Detect member access context: current position is inside a Variable
        // that is itself a child of another Variable (i.e. after -> or ::).
        val innerVar = PsiTreeUtil.getParentOfType(parameters.position, ZephirVariable::class.java)
            ?: return
        val outerVar = innerVar.parent as? ZephirVariable ?: return

        val objectIdList = outerVar.complexId.idList
        if (objectIdList.isEmpty()) return
        val objectName = objectIdList.last().text

        val methodDef = PsiTreeUtil.getParentOfType(parameters.position, ZephirMethodDefinition::class.java)
            ?: return
        val typeName = resolveVariableType(objectName, methodDef) ?: return

        val project = parameters.position.project
        val basePath = project.basePath ?: return
        val baseDir = LocalFileSystem.getInstance().findFileByPath(basePath) ?: return

        suggestMembersInDirectory(baseDir, typeName, PsiManager.getInstance(project), result)
    }

    private fun resolveVariableType(varName: String, methodDef: ZephirMethodDefinition): String? {
        for (args in methodDef.argumentsList) {
            for (arg in args.argumentList) {
                if (arg.id?.text == varName) {
                    return arg.type?.complexId?.idList?.lastOrNull()?.text
                }
            }
        }
        val methodBody = methodDef.methodBody ?: return null
        for (decl in PsiTreeUtil.findChildrenOfType(methodBody, ZephirDeclarationStatement::class.java)) {
            val typeName = decl.type?.complexId?.idList?.lastOrNull()?.text ?: continue
            if (decl.idList.any { it.text == varName }) return typeName
        }
        return null
    }

    private fun suggestMembersInDirectory(
        dir: VirtualFile,
        typeName: String,
        psiManager: PsiManager,
        result: CompletionResultSet
    ) {
        for (child in dir.children) {
            if (child.isDirectory) {
                suggestMembersInDirectory(child, typeName, psiManager, result)
            } else if (child.extension == "zep") {
                val psiFile = psiManager.findFile(child) as? ZephirFile ?: continue
                for (element in psiFile.children) {
                    when {
                        element is ZephirClassDefinition && element.id.text == typeName ->
                            suggestClassMembers(element.classBody ?: continue, result)
                        element is ZephirInterfaceDefinition && element.id.text == typeName ->
                            suggestInterfaceMembers(element.interfaceBody ?: continue, result)
                    }
                }
            }
        }
    }

    private fun suggestClassMembers(classBody: ZephirClassBody, result: CompletionResultSet) {
        for (method in classBody.methodDefinitionList) {
            result.addElement(methodLookup(method.id.text, method.returnType?.typeList?.firstOrNull()?.text))
        }
        for (abstractDef in classBody.abstractMethodDefinitionList) {
            val inner = abstractDef.interfaceMethodDefinition
            result.addElement(methodLookup(inner.id.text, inner.returnType?.typeList?.firstOrNull()?.text))
        }
        for (prop in classBody.propertyDefinitionList) {
            result.addElement(
                LookupElementBuilder.create(prop.id.text)
                    .withTypeText(prop.visibility.text)
            )
        }
    }

    private fun suggestInterfaceMembers(interfaceBody: ZephirInterfaceBody, result: CompletionResultSet) {
        for (method in interfaceBody.interfaceMethodDefinitionList) {
            result.addElement(methodLookup(method.id.text, method.returnType?.typeList?.firstOrNull()?.text))
        }
    }

    private fun methodLookup(name: String, returnType: String?) =
        LookupElementBuilder.create(name)
            .withTypeText(returnType ?: "")
            .withInsertHandler { ctx, _ ->
                ctx.document.insertString(ctx.tailOffset, "()")
                ctx.editor.caretModel.moveToOffset(ctx.tailOffset - 1)
            }
}
