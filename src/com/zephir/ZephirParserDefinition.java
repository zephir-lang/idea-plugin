// Copyright (c) 2014-2019 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.zephir.lexer.ZephirLexerAdapter;
import com.zephir.parser.ZephirParser;
import com.zephir.psi.ZephirFile;
import com.zephir.psi.ZephirTypes;
import org.jetbrains.annotations.NotNull;

public class ZephirParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(ZephirTypes.COMMENT, ZephirTypes.COMMENT_BLOCK);
    public static final TokenSet STRINGS = TokenSet.create(ZephirTypes.STRING, ZephirTypes.SCHAR);
    public static final IFileElementType FILE = new IFileElementType(Language.findInstance(ZephirLanguage.class));

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new ZephirLexerAdapter();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new ZephirParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return STRINGS;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return ZephirTypes.Factory.createElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider provider) {
        return new ZephirFile(provider);
    }
}
