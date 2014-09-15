package com.zephir.syntax;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import com.zephir.lexer.ZephirLexerAdapter;
import com.zephir.psi.ZephirTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nikita Gusakov
 */
public class ZephirSyntaxHighlighter implements SyntaxHighlighter {
    public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey("IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey("BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey LINE_COMMENT = TextAttributesKey.createTextAttributesKey("LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey DOC_COMMENT = TextAttributesKey.createTextAttributesKey("DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey("NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey BRACKETS = TextAttributesKey.createTextAttributesKey("BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey BRACES = TextAttributesKey.createTextAttributesKey("BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey PARENTHESES = TextAttributesKey.createTextAttributesKey("PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey COMMA = TextAttributesKey.createTextAttributesKey("COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey SYMBOL = TextAttributesKey.createTextAttributesKey("SYMBOL", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    public static final TextAttributesKey SEMICOLON = TextAttributesKey.createTextAttributesKey("SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey OPERATOR = TextAttributesKey.createTextAttributesKey("OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey PATH_SEPARATOR = TextAttributesKey.createTextAttributesKey("PATH_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey ATTRIBUTE = TextAttributesKey.createTextAttributesKey("ATTRIBUTE", DefaultLanguageHighlighterColors.METADATA);

    public static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    public static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
    public static final TextAttributesKey[] BLOCK_COMMENT_KEYS = new TextAttributesKey[]{BLOCK_COMMENT};
    public static final TextAttributesKey[] LINE_COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT};
    public static final TextAttributesKey[] DOC_COMMENT_KEYS = new TextAttributesKey[]{DOC_COMMENT};
    public static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    public static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    public static final TextAttributesKey[] BRACKET_KEYS = new TextAttributesKey[]{BRACKETS};
    public static final TextAttributesKey[] BRACE_KEYS = new TextAttributesKey[]{BRACES};
    public static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};
    public static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{COMMA};
    public static final TextAttributesKey[] SYMBOL_KEYS = new TextAttributesKey[]{SYMBOL};
    public static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[]{SEMICOLON};
    public static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{OPERATOR};
    public static final TextAttributesKey[] PATH_SEPARATOR_KEYS = new TextAttributesKey[]{PATH_SEPARATOR};
    public static final TextAttributesKey[] ATTRIBUTE_KEYS = new TextAttributesKey[]{ATTRIBUTE};
    public static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new ZephirLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType type) {
        if (type == ZephirTypes.LET ||
                type == ZephirTypes.ECHO ||
                type == ZephirTypes.CONST ||
                type == ZephirTypes.IF ||
                type == ZephirTypes.ELSE ||
                type == ZephirTypes.SWITCH ||
                type == ZephirTypes.CASE ||
                type == ZephirTypes.DEFAULT ||
                type == ZephirTypes.DO ||
                type == ZephirTypes.WHILE ||
                type == ZephirTypes.FOR ||
                type == ZephirTypes.LOOP ||
                type == ZephirTypes.REVERSE ||
                type == ZephirTypes.BREAK ||
                type == ZephirTypes.CONTINUE ||
                type == ZephirTypes.IN ||
                type == ZephirTypes.NEW ||
                type == ZephirTypes.RETURN ||
                type == ZephirTypes.REQUIRE ||
                type == ZephirTypes.CLONE ||
                type == ZephirTypes.EMPTY ||
                type == ZephirTypes.TYPEOF ||
                type == ZephirTypes.INSTANCEOF ||
                type == ZephirTypes.LIKELY ||
                type == ZephirTypes.UNLIKELY ||
                type == ZephirTypes.ISSET ||
                type == ZephirTypes.UNSET ||
                type == ZephirTypes.THROW ||
                type == ZephirTypes.FETCH ||
                type == ZephirTypes.TRY ||
                type == ZephirTypes.CATCH ||
                type == ZephirTypes.NAMESPACE ||
                type == ZephirTypes.USE ||
                type == ZephirTypes.AS ||
                // OOP
                type == ZephirTypes.INTEFACE ||
                type == ZephirTypes.CLASS ||
                type == ZephirTypes.FUNCTION ||
                type == ZephirTypes.EXTENDS ||
                type == ZephirTypes.IMPLEMENTS ||
                type == ZephirTypes.ABSTRACT ||
                type == ZephirTypes.FINAL ||
                type == ZephirTypes.PUBLIC ||
                type == ZephirTypes.PROTECTED ||
                type == ZephirTypes.PRIVATE ||
                type == ZephirTypes.STATIC ||
                type == ZephirTypes.INLINE ||
                type == ZephirTypes.DEPRECATED) {
            return KEYWORD_KEYS;
        }

        if (type == ZephirTypes.VAR ||
                type == ZephirTypes.VOID ||
                type == ZephirTypes.INT ||
                type == ZephirTypes.UINT ||
                type == ZephirTypes.LONG ||
                type == ZephirTypes.ULONG ||
                type == ZephirTypes.CHAR ||
                type == ZephirTypes.UCHAR ||
                type == ZephirTypes.DOUBLE ||
                type == ZephirTypes.BOOL ||
                type == ZephirTypes.STRING ||
                type == ZephirTypes.ARRAY ||
                type == ZephirTypes.OBJECT ||
                type == ZephirTypes.CALLABLE ||
                type == ZephirTypes.RESOURCE) {
            return ATTRIBUTE_KEYS;
        }

        if (type == ZephirTypes.IDENTIFIER) {
            return IDENTIFIER_KEYS;
        }

        if (type == ZephirTypes.COMMENT_PATTERN) {
            return LINE_COMMENT_KEYS;
        }

        if (type == ZephirTypes.STRING_PATTERN || type == ZephirTypes.SCHAR_PATTERN) {
            return STRING_KEYS;
        }

        if (type == ZephirTypes.INTEGER_PATTERN || type == ZephirTypes.DOUBLE_PATTERN) {
            return NUMBER_KEYS;
        }

        if (type == ZephirTypes.PARENTHESES_OPEN || type == ZephirTypes.PARENTHESES_CLOSE) {
            return PARENTHESES_KEYS;
        }

        if (type == ZephirTypes.BRACKET_OPEN || type == ZephirTypes.BRACKET_CLOSE) {
            return BRACE_KEYS;
        }

        if (type == ZephirTypes.SBRACKET_OPEN || type == ZephirTypes.SBRACKET_CLOSE) {
            return BRACKET_KEYS;
        }

        if (type == ZephirTypes.AT ||
                type == ZephirTypes.NOT ||
                type == ZephirTypes.AND ||
                type == ZephirTypes.OR ||
                type == ZephirTypes.BITWISE_AND ||
                type == ZephirTypes.BITWISE_OR ||
                type == ZephirTypes.BITWISE_XOR ||
                type == ZephirTypes.BITWISE_SHIFTLEFT ||
                type == ZephirTypes.BITWISE_SHIFTRIGHT ||
                type == ZephirTypes.ASSIGN ||
                type == ZephirTypes.ADDASSIGN ||
                type == ZephirTypes.SUBASSIGN ||
                type == ZephirTypes.MULASSIGN ||
                type == ZephirTypes.DIVASSIGN ||
                type == ZephirTypes.MODASSIGN ||
                type == ZephirTypes.CONCATASSIGN ||
                type == ZephirTypes.EQUALS ||
                type == ZephirTypes.NOTEQUALS ||
                type == ZephirTypes.IDENTICAL ||
                type == ZephirTypes.NOTIDENTICAL ||
                type == ZephirTypes.LESSEQUAL ||
                type == ZephirTypes.GREATEREQUAL ||
                type == ZephirTypes.LESS ||
                type == ZephirTypes.GREATER ||
                type == ZephirTypes.ARROW ||
                type == ZephirTypes.DOUBLECOLON ||
                type == ZephirTypes.DOT ||
                type == ZephirTypes.ADD ||
                type == ZephirTypes.SUB ||
                type == ZephirTypes.MUL ||
                type == ZephirTypes.EXP ||
                type == ZephirTypes.DIV ||
                type == ZephirTypes.MOD ||
                type == ZephirTypes.INCR ||
                type == ZephirTypes.DECR ||
                type == ZephirTypes.COLON ||
                type == ZephirTypes.DOTCOMMA ||
                type == ZephirTypes.COMMA ||
                type == ZephirTypes.QUESTION) {
            return OPERATOR_KEYS;
        }

        if (type == ZephirTypes.COMMA) {
            return COMMA_KEYS;
        }
        if (type == ZephirTypes.DOTCOMMA) {
            return SEMICOLON_KEYS;
        }
        if (type == ZephirTypes.DOUBLECOLON) {
            return PATH_SEPARATOR_KEYS;
        }


        return EMPTY_KEYS;
    }
}
