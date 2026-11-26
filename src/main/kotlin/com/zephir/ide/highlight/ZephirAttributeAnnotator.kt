// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.highlight

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.zephir.ide.color.ZephirColor
import com.zephir.lang.core.psi.ZephirAttributeGroup
import com.zephir.lang.core.psi.ZephirTypes

/**
 * Colours the `#[`, the attribute names and the closing `]` of an attribute group. Attribute names are identifiers
 * to the lexer, so the lexer-based highlighter cannot colour them. The arguments keep their normal highlighting.
 */
class ZephirAttributeAnnotator : Annotator, DumbAware {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is ZephirAttributeGroup) return

        element.node.getChildren(null)
            .filter { it.elementType == ZephirTypes.ATTRIBUTE_OPEN || it.elementType == ZephirTypes.SBRACKET_CLOSE }
            .forEach { color(it.textRange, holder) }
        element.attributeList.forEach { attribute ->
            attribute.attributeName?.let { color(it.textRange, holder) }
        }
    }

    private fun color(range: TextRange, holder: AnnotationHolder) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(range)
            .textAttributes(ZephirColor.ATTRIBUTE.textAttributesKey)
            .create()
    }
}
