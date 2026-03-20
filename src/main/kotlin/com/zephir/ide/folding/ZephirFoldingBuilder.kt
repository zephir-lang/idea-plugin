// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor
import com.zephir.lang.core.psi.*

class ZephirFoldingBuilder : FoldingBuilderEx(), DumbAware {

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val descriptors = mutableListOf<FoldingDescriptor>()
        root.accept(object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) {
                when (element) {
                    is ZephirClassBody,
                    is ZephirInterfaceBody,
                    is ZephirCodeBlock -> if (spansMultipleLines(element, document)) {
                        descriptors.add(FoldingDescriptor(element.node, element.textRange))
                    }
                    is ZephirCBlock -> if (element.textLength > 6) {
                        descriptors.add(FoldingDescriptor(element.node, element.textRange))
                    }
                }
                if (element.node.elementType == ZephirTypes.COMMENT_BLOCK && element.text.contains('\n')) {
                    descriptors.add(FoldingDescriptor(element.node, element.textRange))
                }
                super.visitElement(element)
            }
        })
        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String = when {
        node.elementType == ZephirTypes.COMMENT_BLOCK -> "/* ... */"
        node.psi is ZephirCBlock -> "%{ ... }%"
        else -> "{...}"
    }

    override fun isCollapsedByDefault(node: ASTNode) = false

    private fun spansMultipleLines(element: PsiElement, document: Document): Boolean {
        val range = element.textRange
        return document.getLineNumber(range.startOffset) != document.getLineNumber(range.endOffset)
    }
}
