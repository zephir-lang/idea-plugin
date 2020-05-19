// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.psi.tree.TokenSet
import com.zephir.lang.core.stubs.ZephirStubFileElementType
import com.zephir.lang.core.psi.ZEPHIR_COMMENTS
import com.zephir.lang.core.psi.ZephirFile
import com.zephir.lang.core.lexer.ZephirLexerAdapter
import com.zephir.lang.core.psi.ZephirTypes

class ZephirParserDefinition : ParserDefinition {
    companion object {
        val FILE = ZephirStubFileElementType()
    }

    override fun getFileNodeType() = FILE
    override fun getWhitespaceTokens() = TokenSet.create(TokenType.WHITE_SPACE)
    override fun getCommentTokens() = ZEPHIR_COMMENTS
    override fun getStringLiteralElements() = TokenSet.create(ZephirTypes.STRING, ZephirTypes.SCHAR)

    override fun createFile(viewProvider: FileViewProvider) = ZephirFile(viewProvider)

    override fun createParser(project: Project?) = ZephirParser()
    override fun createLexer(project: Project?) = ZephirLexerAdapter()

    override fun createElement(node: ASTNode?): PsiElement = ZephirTypes.Factory.createElement(node)
}

