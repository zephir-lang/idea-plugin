// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.navigation

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.lang.core.psi.ZephirClassDefinition
import com.zephir.lang.core.psi.ZephirComplexId
import com.zephir.lang.core.psi.ZephirFile
import com.zephir.lang.core.psi.ZephirImportStatement
import com.zephir.lang.core.psi.ZephirInterfaceDefinition
import com.zephir.lang.core.psi.ZephirNamespaceStatement

/**
 * Handles Ctrl+click Go To Declaration for Zephir class and interface references.
 *
 * Resolution strategy (in order):
 * 1. Qualified name (`Phalcon\Storage\Adapter\Libmemcached`): walk the namespace segments
 *    as directory components and open the matching `.zep` file precisely.
 * 2. Unqualified name (`MyClass`): scan all `.zep` files under the project base for a
 *    matching class or interface declaration.
 * 3. Alias fallback: if no class is found, look for a `use ... as Name` statement in the
 *    current file and navigate to that alias so the user can Ctrl+click the real path.
 */
class ZephirGotoDeclarationHandler : GotoDeclarationHandler {

    override fun getGotoDeclarationTargets(
        sourceElement: PsiElement?,
        offset: Int,
        editor: Editor
    ): Array<PsiElement>? {
        sourceElement ?: return null

        val complexId = PsiTreeUtil.getParentOfType(sourceElement, ZephirComplexId::class.java)
            ?: return null

        if (complexId.parent is ZephirNamespaceStatement) return null

        val idList = complexId.idList.takeIf { it.isNotEmpty() } ?: return null
        val name = idList.last().text
        val namespaceParts = idList.dropLast(1).map { it.text }

        val project = sourceElement.project
        val psiManager = PsiManager.getInstance(project)
        val basePath = project.basePath ?: return null
        val baseDir = LocalFileSystem.getInstance().findFileByPath(basePath) ?: return null

        // 1. Qualified name: use namespace segments to navigate to the exact directory.
        if (namespaceParts.isNotEmpty()) {
            findByNamespacePath(baseDir, namespaceParts, name, psiManager)?.let { return arrayOf(it) }
        }

        // 2. Unqualified name: scan all .zep files.
        findInDirectory(baseDir, name, psiManager)?.let { return arrayOf(it) }

        // 3. Alias fallback: look for 'use ... as Name' in the current file and navigate
        //    to the alias declaration so the user can Ctrl+click the real class path.
        val containingFile = sourceElement.containingFile as? ZephirFile ?: return null
        for (element in containingFile.children) {
            if (element is ZephirImportStatement) {
                val alias = element.id
                if (alias != null && alias.text == name) return arrayOf(alias)
            }
        }

        return null
    }

    /**
     * Resolves a fully-qualified Zephir name by mapping each namespace segment to a
     * directory component (case-insensitive), then finds the `.zep` file for the class.
     */
    private fun findByNamespacePath(
        baseDir: VirtualFile,
        namespaceParts: List<String>,
        className: String,
        psiManager: PsiManager
    ): PsiElement? {
        var dir = baseDir
        for (part in namespaceParts) {
            dir = dir.children.firstOrNull { it.isDirectory && it.name.equals(part, ignoreCase = true) }
                ?: return null
        }
        val file = dir.children.firstOrNull {
            !it.isDirectory && it.extension == "zep" && it.nameWithoutExtension.equals(className, ignoreCase = true)
        } ?: return null
        val psiFile = psiManager.findFile(file) as? ZephirFile ?: return null
        for (element in psiFile.children) {
            if (element is ZephirClassDefinition && element.id.text == className) return element.id
            if (element is ZephirInterfaceDefinition && element.id.text == className) return element.id
        }
        return psiFile
    }

    private fun findInDirectory(dir: VirtualFile, name: String, psiManager: PsiManager): PsiElement? {
        for (child in dir.children) {
            if (child.isDirectory) {
                findInDirectory(child, name, psiManager)?.let { return it }
            } else if (child.extension == "zep") {
                val psiFile = psiManager.findFile(child) as? ZephirFile ?: continue
                for (element in psiFile.children) {
                    if (element is ZephirClassDefinition && element.id.text == name) return element.id
                    if (element is ZephirInterfaceDefinition && element.id.text == name) return element.id
                }
            }
        }
        return null
    }
}
