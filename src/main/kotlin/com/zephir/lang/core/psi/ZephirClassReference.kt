// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.psi

import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase

/**
 * A reference from a [ZephirComplexId] usage site to the [ZephirClassDefinition] or
 * [ZephirInterfaceDefinition] that declares the name. The reference range covers only
 * the last [ZephirId] segment so that clicking on a qualified name like
 * `Phalcon\Mvc\Controller` navigates via `Controller`, not the namespace prefix.
 */
class ZephirClassReference(element: ZephirComplexId) : PsiReferenceBase<ZephirComplexId>(element, true) {

    override fun calculateDefaultRangeInElement(): TextRange {
        val lastId = element.idList.lastOrNull() ?: return TextRange.EMPTY_RANGE
        val start = lastId.startOffsetInParent
        return TextRange(start, start + lastId.textLength)
    }

    override fun resolve(): PsiElement? {
        val name = element.idList.lastOrNull()?.text ?: return null
        val project = element.project
        val psiManager = PsiManager.getInstance(project)

        val basePath = project.basePath ?: return null
        val baseDir = LocalFileSystem.getInstance().findFileByPath(basePath) ?: return null
        return findInDirectory(baseDir, name, psiManager)
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

    override fun getVariants(): Array<Any> = emptyArray()
}
