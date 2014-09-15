package com.zephir.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * @author Nikita Gusakov
 */
public class ZephirLexerAdapter extends FlexAdapter {
    public ZephirLexerAdapter() {
        super(new ZephirLexer((Reader) null));
    }
}
