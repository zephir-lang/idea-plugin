/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * Licensed under the MIT License (MIT);
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

package com.zephir.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class ZephirLexerAdapter extends FlexAdapter {
    public ZephirLexerAdapter() {
        super(new ZephirLexer((Reader) null));
    }
}
