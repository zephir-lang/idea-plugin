package com.zephir;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nikita Gusakov
 */
public class ZephirSyntaxHighlighter implements SyntaxHighlighter {
    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new ZephirLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
        return new TextAttributesKey[0];
    }
}
