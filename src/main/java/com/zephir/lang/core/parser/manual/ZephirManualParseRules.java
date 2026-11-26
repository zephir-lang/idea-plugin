// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.parser.manual;

import com.intellij.lang.BracePair;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.zephir.lang.core.psi.ZephirTypes;

import java.util.Arrays;

public class ZephirManualParseRules extends GeneratedParserUtilBase {
    private static final TokenSet STATEMENT_STOP = TokenSet.create(
        ZephirTypes.BRACKET_CLOSE, ZephirTypes.LET, ZephirTypes.ECHO, ZephirTypes.IF, ZephirTypes.ELSE,
        ZephirTypes.ELSEIF, ZephirTypes.SWITCH, ZephirTypes.CASE, ZephirTypes.DEFAULT, ZephirTypes.DO,
        ZephirTypes.WHILE, ZephirTypes.FOR, ZephirTypes.LOOP, ZephirTypes.BREAK, ZephirTypes.CONTINUE,
        ZephirTypes.RETURN, ZephirTypes.REQUIRE, ZephirTypes.REQUIRE_ONCE, ZephirTypes.YIELD,
        ZephirTypes.THROW, ZephirTypes.TRY, ZephirTypes.CATCH, ZephirTypes.UNSET, ZephirTypes.CBLOCK,
        ZephirTypes.PUBLIC, ZephirTypes.PROTECTED, ZephirTypes.PRIVATE, ZephirTypes.INTERNAL,
        ZephirTypes.INTERFACE, ZephirTypes.TRAIT, ZephirTypes.NAMESPACE
    );

    private static final TokenSet STATEMENT_END = TokenSet.create(
        ZephirTypes.DOTCOMMA, ZephirTypes.BRACKET_CLOSE, ZephirTypes.CBLOCK
    );

    private static final TokenSet DECLARATION_START = TokenSet.create(
        ZephirTypes.NAMESPACE, ZephirTypes.USE, ZephirTypes.CLASS, ZephirTypes.INTERFACE, ZephirTypes.TRAIT,
        ZephirTypes.ABSTRACT, ZephirTypes.FINAL, ZephirTypes.FUNCTION, ZephirTypes.ATTRIBUTE_OPEN, ZephirTypes.CBLOCK
    );

    private static final TokenSet MEMBER_START = TokenSet.create(
        ZephirTypes.INTERNAL, ZephirTypes.PUBLIC, ZephirTypes.PROTECTED, ZephirTypes.PRIVATE, ZephirTypes.STATIC,
        ZephirTypes.INLINE, ZephirTypes.DEPRECATED, ZephirTypes.ABSTRACT, ZephirTypes.FINAL, ZephirTypes.READONLY,
        ZephirTypes.CONST, ZephirTypes.FUNCTION, ZephirTypes.USE, ZephirTypes.ATTRIBUTE_OPEN, ZephirTypes.BRACKET_CLOSE
    );

    private static final TokenSet TRIVIA = TokenSet.create(
        TokenType.WHITE_SPACE, ZephirTypes.COMMENT, ZephirTypes.COMMENT_BLOCK
    );

    /**
     * Gives the base parser's state the language's brace pairs less `<` and `>`, which the editor pairs
     * for types but which are comparison operators in code the recovery has to step over.
     */
    public static PsiBuilder adapt_builder_(
        IElementType root,
        PsiBuilder builder,
        PsiParser parser,
        TokenSet[] extendsSets
    ) {
        PsiBuilder adapted = GeneratedParserUtilBase.adapt_builder_(root, builder, parser, extendsSets);
        ErrorState state = ErrorState.get(adapted);
        if (state.braces != null) {
            state.braces = Arrays.stream(state.braces)
                .filter(pair -> pair.getLeftBraceType() != ZephirTypes.LESS)
                .toArray(BracePair[]::new);
        }

        return adapted;
    }

    /**
     * Tells the recovery of a statement whether the next token still belongs to the broken statement: a
     * `{`, a token that starts a statement and the `}` that closes the block never do, and nothing does once
     * the previous token ended a statement.
     */
    public static boolean statementRecover(PsiBuilder builder, int level) {
        IElementType current = builder.getTokenType();
        if (current == null) return false;
        if (current == ZephirTypes.BRACKET_OPEN || STATEMENT_STOP.contains(current)) return false;

        return !STATEMENT_END.contains(previousToken(builder));
    }

    /**
     * Consumes the tokens of a statement that no rule accepts, up to and including its `;`, as one error.
     * Braces opened inside the statement are consumed with their match. It stops, without consuming, at the
     * `}` that closes the block or at a token that starts a statement or a member.
     */
    public static boolean skipStatement(PsiBuilder builder, int level) {
        IElementType first = builder.getTokenType();
        if (first == null || STATEMENT_STOP.contains(first)) return false;

        PsiBuilder.Marker marker = builder.mark();
        int depth = 0;
        IElementType token = first;
        while (token != null) {
            if (depth == 0 && STATEMENT_STOP.contains(token)) break;
            if (token == ZephirTypes.BRACKET_OPEN) depth++;
            if (token == ZephirTypes.BRACKET_CLOSE) depth--;
            builder.advanceLexer();
            if (token == ZephirTypes.DOTCOMMA && depth == 0) break;
            token = builder.getTokenType();
        }
        marker.error("<statement> expected");

        return true;
    }

    /**
     * Consumes the tokens that start no declaration, up to the next token that does, as one error, so that the
     * top level always moves on.
     */
    public static boolean skipDeclaration(PsiBuilder builder, int level) {
        return skipRun(builder, "<declaration> expected", DECLARATION_START, false);
    }

    /**
     * Consumes the tokens that start no member, up to the next token that does or the `}` that closes the body, as
     * one error. The `}` is left alone.
     */
    public static boolean skipMember(PsiBuilder builder, int level) {
        return skipRun(builder, "<member> expected", MEMBER_START, true);
    }

    /**
     * Consumes what sits between a declaration's name and its body, up to the `{` or the next declaration, as one
     * error.
     */
    public static boolean skipHeader(PsiBuilder builder, int level) {
        IElementType first = builder.getTokenType();
        if (first == null || first == ZephirTypes.BRACKET_OPEN || isStart(first)) return false;

        PsiBuilder.Marker marker = builder.mark();
        IElementType token = first;
        while (token != null && token != ZephirTypes.BRACKET_OPEN && !isStart(token)) {
            builder.advanceLexer();
            token = builder.getTokenType();
        }
        marker.error("'{' expected");

        return true;
    }

    /** Consumes what sits where a type belongs, up to the `{` or `;` that ends the signature, as one error. */
    public static boolean skipType(PsiBuilder builder, int level) {
        IElementType first = builder.getTokenType();
        if (first == null || first == ZephirTypes.BRACKET_OPEN || first == ZephirTypes.DOTCOMMA || isStart(first)) {
            return false;
        }

        PsiBuilder.Marker marker = builder.mark();
        IElementType token = first;
        while (token != null && token != ZephirTypes.BRACKET_OPEN && token != ZephirTypes.DOTCOMMA
            && !isStart(token)) {
            builder.advanceLexer();
            token = builder.getTokenType();
        }
        marker.error("<type> expected");

        return true;
    }

    private static boolean isStart(IElementType token) {
        return MEMBER_START.contains(token) || DECLARATION_START.contains(token);
    }

    private static boolean skipRun(PsiBuilder builder, String message, TokenSet stop, boolean closeStops) {
        IElementType first = builder.getTokenType();
        if (first == null || (closeStops && first == ZephirTypes.BRACKET_CLOSE)) return false;

        PsiBuilder.Marker marker = builder.mark();
        int depth = 0;
        boolean consumed = false;
        IElementType token = first;
        while (token != null) {
            if (consumed && depth == 0 && (stop.contains(token) || isStart(token))) break;
            if (token == ZephirTypes.BRACKET_OPEN) depth++;
            if (token == ZephirTypes.BRACKET_CLOSE && depth > 0) depth--;
            builder.advanceLexer();
            consumed = true;
            token = builder.getTokenType();
        }
        marker.error(message);

        return true;
    }

    private static IElementType previousToken(PsiBuilder builder) {
        int steps = -1;
        IElementType token = builder.rawLookup(steps);
        while (token != null && TRIVIA.contains(token)) {
            token = builder.rawLookup(--steps);
        }

        return token;
    }
}
