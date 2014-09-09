package com.zephir;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.zephir.psi.ZephirTypes;
import com.intellij.psi.TokenType;

%%

%class ZephirLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF= \n|\r|\r\n
WHITE_SPACE=[\ \t\f]
INTEGER = ([\-]?[0-9]+)|([\-]?[0][x][0-9A-Fa-f]+)
DOUBLE = ([\-]?[0-9]+[\.][0-9]+)
SCHAR = (['] ([\\][']|[\\].|[\001-\377]\[\\'])* ['])
STRING = (["] ([\\]["]|[\\].|[\001-\377]\[\\"])* ["])
COMMENT = ("/*"([^*]+|[*]+[^/*])*[*]*"*/")
SLCOMMENT = ("//"[^\r\n]*)
CBLOCK = ("%{"([^}]+|[}]+[^%{])*"}%")
IDENTIFIER = [\\_\$]?[_a-zA-Z\\][a-zA-Z0-9\_\\]*

%state WAITING_VALUE

%%

. {
    return TokenType.BAD_CHARACTER;
}
