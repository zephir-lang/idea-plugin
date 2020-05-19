// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.color

import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

enum class ZephirColor(humanName: String, default: TextAttributesKey) {
    BRACES("Braces and Operators//Braces", Default.BRACES),
    BRACKETS("Braces and Operators//Brackets", Default.BRACKETS),
    COMMA("Braces and Operators//Comma", Default.COMMA),
    OPERATOR("Braces and Operators//Operators", Default.OPERATION_SIGN),
    PARENTHESIS("Braces and Operators//Parentheses", Default.PARENTHESES),
    SEMICOLON("Braces and Operators//Semicolon", Default.SEMICOLON),
    COLON("Braces and Operators//Colon", Default.OPERATION_SIGN),
    LINE_COMMENT("Comments", Default.LINE_COMMENT),
    IDENTIFIER("Identifiers//Default", Default.IDENTIFIER),
    DATA_TYPE("Identifiers//Primitive Type Hint", Default.METADATA),
    KEYWORD("Keywords", Default.KEYWORD),
    NUMBER("Numbers", Default.NUMBER),
    C_BLOCK("C Block", Default.DOC_COMMENT),
    BLOCK_COMMENT("PHPDoc//Text", Default.BLOCK_COMMENT),
    STRING("Strings//Characters", Default.STRING);

    // TODO(serghei): Do we need this? Also see com.zephir.ide.highlight.ZephirSyntaxHighlighter
    // BAD_CHAR("Unknown Character", HighlighterColors.BAD_CHARACTER),

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("com.zephir.$name", default)
    val attributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}
