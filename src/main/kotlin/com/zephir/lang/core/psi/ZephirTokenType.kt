// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.psi

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.zephir.lang.core.ZephirLanguage
import com.zephir.lang.core.psi.ZephirTypes.*

/** The type of PSI tree leaf nodes (tokens) */
class ZephirTokenType(debugName: String) : IElementType(debugName, ZephirLanguage)

fun tokenSetOf(vararg tokens: IElementType) = TokenSet.create(*tokens)

val ZEPHIR_COMMENTS = tokenSetOf(
    COMMENT,
    COMMENT_BLOCK
)

val ZEPHIR_KEYWORDS = tokenSetOf(
    LET,
    ECHO,
    CONST,
    IF,
    ELSE,
    ELSEIF,
    SWITCH,
    CASE,
    DEFAULT,
    DO,
    WHILE,
    FOR,
    LOOP,
    REVERSE,
    BREAK,
    CONTINUE,
    IN,
    NEW,
    RETURN,
    REQUIRE,
    CLONE,
    EMPTY,
    TYPEOF,
    INSTANCEOF,
    LIKELY,
    UNLIKELY,
    ISSET,
    UNSET,
    THROW,
    FETCH,
    TRY,
    CATCH,
    NAMESPACE,
    USE,
    AS,

    // OOP
    INTERNAL,
    INTEFACE,
    CLASS,
    FUNCTION,
    EXTENDS,
    IMPLEMENTS,
    ABSTRACT,
    FINAL,
    PUBLIC,
    PROTECTED,
    PRIVATE,
    STATIC,
    INLINE,
    DEPRECATED,

    // const value - primitives of language
    TRUE,
    FALSE,
    NULL
)

val ZEPHIR_DATA_TYPES = tokenSetOf(
    TYPE_VAR,
    TYPE_VOID,
    TYPE_INT,
    TYPE_UINT,
    TYPE_LONG,
    TYPE_ULONG,
    TYPE_CHAR,
    TYPE_UCHAR,
    TYPE_DOUBLE,
    TYPE_BOOL,
    TYPE_STRING,
    TYPE_ARRAY,
    TYPE_OBJECT,
    TYPE_CALLABLE,
    TYPE_RESOURCE
)

val ZEPHIR_OPERATORS = tokenSetOf(
    AT,
    NOT,
    AND,
    OR,
    BITWISE_AND,
    BITWISE_OR,
    BITWISE_XOR,
    BITWISE_SHIFTLEFT,
    BITWISE_SHIFTRIGHT,
    ASSIGN,
    ADDASSIGN,
    SUBASSIGN,
    MULASSIGN,
    EXPASSIGN,
    DIVASSIGN,
    MODASSIGN,
    CONCATASSIGN,
    EQUALS,
    NOTEQUALS,
    IDENTICAL,
    NOTIDENTICAL,
    LESSEQUAL,
    GREATEREQUAL,
    LESS,
    GREATER,
    ARROW,
    DOT,
    ADD,
    SUB,
    MUL,
    EXP,
    DIV,
    MOD,
    INCR,
    DECR,
    COLON,
    QUESTION
)
