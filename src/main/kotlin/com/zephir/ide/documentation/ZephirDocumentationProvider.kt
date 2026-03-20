// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.zephir.lang.core.psi.ZephirAbstractMethodDefinition
import com.zephir.lang.core.psi.ZephirArguments
import com.zephir.lang.core.psi.ZephirClassDefinition
import com.zephir.lang.core.psi.ZephirConstantDefinition
import com.zephir.lang.core.psi.ZephirInterfaceDefinition
import com.zephir.lang.core.psi.ZephirInterfaceMethodDefinition
import com.zephir.lang.core.psi.ZephirMethodDefinition
import com.zephir.lang.core.psi.ZephirPropertyDefinition
import com.zephir.lang.core.psi.ZephirReturnType

/**
 * Provides Quick Documentation (Ctrl+Q / hover) for Zephir PSI elements by reading
 * the preceding docblock (`/** ... */`) for classes, interfaces, methods, properties,
 * and constants.
 */
class ZephirDocumentationProvider : AbstractDocumentationProvider() {

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val target = findDocumentableElement(element) ?: return null
        val comment = findPrecedingDocComment(target) ?: return null
        return renderDoc(comment.text, target)
    }

    private fun findDocumentableElement(element: PsiElement?): PsiElement? {
        var e = element
        while (e != null && e !is PsiFile) {
            if (isDocumentable(e)) return e
            e = e.parent
        }
        return null
    }

    private fun isDocumentable(e: PsiElement) =
        e is ZephirClassDefinition ||
        e is ZephirInterfaceDefinition ||
        e is ZephirMethodDefinition ||
        e is ZephirAbstractMethodDefinition ||
        e is ZephirInterfaceMethodDefinition ||
        e is ZephirPropertyDefinition ||
        e is ZephirConstantDefinition

    private fun findPrecedingDocComment(element: PsiElement): PsiComment? {
        var prev = element.prevSibling
        while (prev is PsiWhiteSpace) prev = prev.prevSibling
        return (prev as? PsiComment)?.takeIf { it.text.startsWith("/**") }
    }

    private fun renderDoc(commentText: String, element: PsiElement): String {
        val lines = commentText
            .removePrefix("/**").removeSuffix("*/")
            .lines()
            .map { it.trim().removePrefix("*").trim() }
            .filter { it.isNotEmpty() }

        val descLines = lines.takeWhile { !it.startsWith("@") }
        val tagLines  = lines.dropWhile  { !it.startsWith("@") }

        return buildString {
            append(DocumentationMarkup.DEFINITION_START)
            appendSignature(element)
            append(DocumentationMarkup.DEFINITION_END)

            if (descLines.isNotEmpty()) {
                append(DocumentationMarkup.CONTENT_START)
                append(descLines.joinToString("<br/>"))
                append(DocumentationMarkup.CONTENT_END)
            }

            val tags = parseTags(tagLines)
            if (tags.isNotEmpty()) {
                append(DocumentationMarkup.SECTIONS_START)
                for ((tag, content) in tags) {
                    append(DocumentationMarkup.SECTION_HEADER_START)
                    append(tag.removePrefix("@").replaceFirstChar { it.uppercaseChar() })
                    append(":")
                    append(DocumentationMarkup.SECTION_SEPARATOR)
                    append("<p>$content</p>")
                    append(DocumentationMarkup.SECTION_END)
                }
                append(DocumentationMarkup.SECTIONS_END)
            }
        }
    }

    private fun StringBuilder.appendSignature(element: PsiElement) {
        when (element) {
            is ZephirClassDefinition ->
                append("<b>class</b> ${element.id.text}")
            is ZephirInterfaceDefinition ->
                append("<b>interface</b> ${element.id.text}")
            is ZephirMethodDefinition ->
                append("<b>fun</b> ${methodSignature(element.id.text, element.argumentsList, element.returnType)}")
            is ZephirAbstractMethodDefinition -> {
                val inner = element.interfaceMethodDefinition
                append("<b>abstract fun</b> ${methodSignature(inner.id.text, inner.argumentsList, inner.returnType)}")
            }
            is ZephirInterfaceMethodDefinition ->
                append("<b>fun</b> ${methodSignature(element.id.text, element.argumentsList, element.returnType)}")
            is ZephirPropertyDefinition ->
                append("<b>var</b> ${element.id.text}")
            is ZephirConstantDefinition ->
                append("<b>const</b> ${element.id.text}")
        }
    }

    private fun methodSignature(name: String, argsList: List<ZephirArguments>, returnType: ZephirReturnType?): String {
        val args = argsList.flatMap { it.argumentList }.joinToString(", ") { arg ->
            buildString {
                arg.type?.text?.let { append("$it ") }
                append(arg.id?.text ?: arg.phpReserved?.text ?: "")
            }
        }
        val ret = returnType?.typeList?.joinToString(" | ") { it.text }?.let { " -> $it" } ?: ""
        return "$name($args)$ret"
    }

    private fun parseTags(tagLines: List<String>): List<Pair<String, String>> {
        val tags = mutableListOf<Pair<String, String>>()
        var currentTag: String? = null
        val currentContent = StringBuilder()

        for (line in tagLines) {
            if (line.startsWith("@")) {
                if (currentTag != null) tags.add(currentTag to currentContent.toString().trim())
                val space = line.indexOf(' ')
                currentTag = if (space == -1) line else line.substring(0, space)
                currentContent.clear()
                if (space != -1) currentContent.append(line.substring(space + 1))
            } else if (currentTag != null) {
                currentContent.append(' ').append(line)
            }
        }
        if (currentTag != null) tags.add(currentTag to currentContent.toString().trim())
        return tags
    }
}
