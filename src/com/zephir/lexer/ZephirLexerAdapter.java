/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * This source file is subject the MIT license, that is bundled with
 * this package in the file LICENSE, and is available through the
 * world-wide-web at the following url:
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
