package com.zephir.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.zephir.psi.ZephirTypes;

%%

%class ZephirLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

COMMENT="//".*
COMMENT_BLOCK=("/*"([^*]+|[*]+[^/*])*[*]*"*/")
IDENTIFIER=[_a-zA-Z][a-zA-Z0-9_]*
INTEGER=([\-]?[0-9]+)|([\-]?[0][x][0-9A-Fa-f]+)
DOUBLE=([\-]?[0-9]+[\.][0-9]+)
CBLOCK = ("%{"([^}]+|[}]+[^%{])*"}%")

HEX_DIGIT = [a-fA-F0-9]
DOUBLE_QUOTE = \x22
SINGLE_QUOTE = \x27
GENERIC_CHAR_TYPES = [acefntxdAGbBpPrRdDhHsSvVwWzZ]
COMMON_ESCAPE = ( [.0\n\r\\] | "x" {HEX_DIGIT} {2} | "u" {HEX_DIGIT} {4} | "U" {HEX_DIGIT} {8} )
SCHAR = {SINGLE_QUOTE} (( [^'\\] | "\\" ( {SINGLE_QUOTE} | {COMMON_ESCAPE} | {GENERIC_CHAR_TYPES}) ) | [^\x20-\x7E]{1,2}) {SINGLE_QUOTE}
STRING = {DOUBLE_QUOTE} ( [^\"\\] | "\\" ( {DOUBLE_QUOTE} | {SINGLE_QUOTE} | {COMMON_ESCAPE} | {GENERIC_CHAR_TYPES}) )* {DOUBLE_QUOTE}

%%
<YYINITIAL> {
  {WHITE_SPACE}        { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

  "namespace"          { yybegin(YYINITIAL); return ZephirTypes.NAMESPACE; }
  "use"                { yybegin(YYINITIAL); return ZephirTypes.USE; }
  "as"                 { yybegin(YYINITIAL); return ZephirTypes.AS; }
  "interface"          { yybegin(YYINITIAL); return ZephirTypes.INTEFACE; }
  "class"              { yybegin(YYINITIAL); return ZephirTypes.CLASS; }
  "function"           { yybegin(YYINITIAL); return ZephirTypes.FUNCTION; }
  "fn"                 { yybegin(YYINITIAL); return ZephirTypes.FUNCTION; }
  "extends"            { yybegin(YYINITIAL); return ZephirTypes.EXTENDS; }
  "implements"         { yybegin(YYINITIAL); return ZephirTypes.IMPLEMENTS; }
  "abstract"           { yybegin(YYINITIAL); return ZephirTypes.ABSTRACT; }
  "final"              { yybegin(YYINITIAL); return ZephirTypes.FINAL; }
  "public"             { yybegin(YYINITIAL); return ZephirTypes.PUBLIC; }
  "protected"          { yybegin(YYINITIAL); return ZephirTypes.PROTECTED; }
  "private"            { yybegin(YYINITIAL); return ZephirTypes.PRIVATE; }
  "static"             { yybegin(YYINITIAL); return ZephirTypes.STATIC; }
  "inline"             { yybegin(YYINITIAL); return ZephirTypes.INLINE; }
  "internal"           { yybegin(YYINITIAL); return ZephirTypes.INTERNAL; }
  "deprecated"         { yybegin(YYINITIAL); return ZephirTypes.DEPRECATED; }
  "var"                { yybegin(YYINITIAL); return ZephirTypes.TYPE_VAR; }
  "void"               { yybegin(YYINITIAL); return ZephirTypes.TYPE_VOID; }
  "int"                { yybegin(YYINITIAL); return ZephirTypes.TYPE_INT; }
  "integer"            { yybegin(YYINITIAL); return ZephirTypes.TYPE_INT; }
  "uint"               { yybegin(YYINITIAL); return ZephirTypes.TYPE_UINT; }
  "long"               { yybegin(YYINITIAL); return ZephirTypes.TYPE_LONG; }
  "ulong"              { yybegin(YYINITIAL); return ZephirTypes.TYPE_ULONG; }
  "char"               { yybegin(YYINITIAL); return ZephirTypes.TYPE_CHAR; }
  "uchar"              { yybegin(YYINITIAL); return ZephirTypes.TYPE_UCHAR; }
  "double"             { yybegin(YYINITIAL); return ZephirTypes.TYPE_DOUBLE; }
  "float"              { yybegin(YYINITIAL); return ZephirTypes.TYPE_DOUBLE; }
  "bool"               { yybegin(YYINITIAL); return ZephirTypes.TYPE_BOOL; }
  "boolean"            { yybegin(YYINITIAL); return ZephirTypes.TYPE_BOOL; }
  "string"             { yybegin(YYINITIAL); return ZephirTypes.TYPE_STRING; }
  "array"              { yybegin(YYINITIAL); return ZephirTypes.TYPE_ARRAY; }
  "object"             { yybegin(YYINITIAL); return ZephirTypes.TYPE_OBJECT; }
  "callable"           { yybegin(YYINITIAL); return ZephirTypes.TYPE_CALLABLE; }
  "resource"           { yybegin(YYINITIAL); return ZephirTypes.TYPE_RESOURCE; }
  "null"               { yybegin(YYINITIAL); return ZephirTypes.NULL; }
  "false"              { yybegin(YYINITIAL); return ZephirTypes.FALSE; }
  "true"               { yybegin(YYINITIAL); return ZephirTypes.TRUE; }
  "let"                { yybegin(YYINITIAL); return ZephirTypes.LET; }
  "echo"               { yybegin(YYINITIAL); return ZephirTypes.ECHO; }
  "const"              { yybegin(YYINITIAL); return ZephirTypes.CONST; }
  "if"                 { yybegin(YYINITIAL); return ZephirTypes.IF; }
  "else"               { yybegin(YYINITIAL); return ZephirTypes.ELSE; }
  "elseif"             { yybegin(YYINITIAL); return ZephirTypes.ELSEIF; }
  "switch"             { yybegin(YYINITIAL); return ZephirTypes.SWITCH; }
  "case"               { yybegin(YYINITIAL); return ZephirTypes.CASE; }
  "default"            { yybegin(YYINITIAL); return ZephirTypes.DEFAULT; }
  "do"                 { yybegin(YYINITIAL); return ZephirTypes.DO; }
  "while"              { yybegin(YYINITIAL); return ZephirTypes.WHILE; }
  "for"                { yybegin(YYINITIAL); return ZephirTypes.FOR; }
  "loop"               { yybegin(YYINITIAL); return ZephirTypes.LOOP; }
  "reverse"            { yybegin(YYINITIAL); return ZephirTypes.REVERSE; }
  "break"              { yybegin(YYINITIAL); return ZephirTypes.BREAK; }
  "continue"           { yybegin(YYINITIAL); return ZephirTypes.CONTINUE; }
  "in"                 { yybegin(YYINITIAL); return ZephirTypes.IN; }
  "new"                { yybegin(YYINITIAL); return ZephirTypes.NEW; }
  "return"             { yybegin(YYINITIAL); return ZephirTypes.RETURN; }
  "require"            { yybegin(YYINITIAL); return ZephirTypes.REQUIRE; }
  "clone"              { yybegin(YYINITIAL); return ZephirTypes.CLONE; }
  "empty"              { yybegin(YYINITIAL); return ZephirTypes.EMPTY; }
  "typeof"             { yybegin(YYINITIAL); return ZephirTypes.TYPEOF; }
  "instanceof"         { yybegin(YYINITIAL); return ZephirTypes.INSTANCEOF; }
  "likely"             { yybegin(YYINITIAL); return ZephirTypes.LIKELY; }
  "unlikely"           { yybegin(YYINITIAL); return ZephirTypes.UNLIKELY; }
  "isset"              { yybegin(YYINITIAL); return ZephirTypes.ISSET; }
  "unset"              { yybegin(YYINITIAL); return ZephirTypes.UNSET; }
  "throw"              { yybegin(YYINITIAL); return ZephirTypes.THROW; }
  "fetch"              { yybegin(YYINITIAL); return ZephirTypes.FETCH; }
  "try"                { yybegin(YYINITIAL); return ZephirTypes.TRY; }
  "catch"              { yybegin(YYINITIAL); return ZephirTypes.CATCH; }
  "("                  { yybegin(YYINITIAL); return ZephirTypes.PARENTHESES_OPEN; }
  ")"                  { yybegin(YYINITIAL); return ZephirTypes.PARENTHESES_CLOSE; }
  "{"                  { yybegin(YYINITIAL); return ZephirTypes.BRACKET_OPEN; }
  "}"                  { yybegin(YYINITIAL); return ZephirTypes.BRACKET_CLOSE; }
  "["                  { yybegin(YYINITIAL); return ZephirTypes.SBRACKET_OPEN; }
  "]"                  { yybegin(YYINITIAL); return ZephirTypes.SBRACKET_CLOSE; }
  "@"                  { yybegin(YYINITIAL); return ZephirTypes.AT; }
  "!"                  { yybegin(YYINITIAL); return ZephirTypes.NOT; }
  "&&"                 { yybegin(YYINITIAL); return ZephirTypes.AND; }
  "||"                 { yybegin(YYINITIAL); return ZephirTypes.OR; }
  "&"                  { yybegin(YYINITIAL); return ZephirTypes.BITWISE_AND; }
  "|"                  { yybegin(YYINITIAL); return ZephirTypes.BITWISE_OR; }
  "^"                  { yybegin(YYINITIAL); return ZephirTypes.BITWISE_XOR; }
  "<<"                 { yybegin(YYINITIAL); return ZephirTypes.BITWISE_SHIFTLEFT; }
  ">>"                 { yybegin(YYINITIAL); return ZephirTypes.BITWISE_SHIFTRIGHT; }
  "="                  { yybegin(YYINITIAL); return ZephirTypes.ASSIGN; }
  "+="                 { yybegin(YYINITIAL); return ZephirTypes.ADDASSIGN; }
  "-="                 { yybegin(YYINITIAL); return ZephirTypes.SUBASSIGN; }
  "*="                 { yybegin(YYINITIAL); return ZephirTypes.MULASSIGN; }
  "**="                { yybegin(YYINITIAL); return ZephirTypes.EXPASSIGN; }
  "/="                 { yybegin(YYINITIAL); return ZephirTypes.DIVASSIGN; }
  "%="                 { yybegin(YYINITIAL); return ZephirTypes.MODASSIGN; }
  ".="                 { yybegin(YYINITIAL); return ZephirTypes.CONCATASSIGN; }
  "=="                 { yybegin(YYINITIAL); return ZephirTypes.EQUALS; }
  "!="                 { yybegin(YYINITIAL); return ZephirTypes.NOTEQUALS; }
  "==="                { yybegin(YYINITIAL); return ZephirTypes.IDENTICAL; }
  "!=="                { yybegin(YYINITIAL); return ZephirTypes.NOTIDENTICAL; }
  "<="                 { yybegin(YYINITIAL); return ZephirTypes.LESSEQUAL; }
  ">="                 { yybegin(YYINITIAL); return ZephirTypes.GREATEREQUAL; }
  "<"                  { yybegin(YYINITIAL); return ZephirTypes.LESS; }
  ">"                  { yybegin(YYINITIAL); return ZephirTypes.GREATER; }
  "->"                 { yybegin(YYINITIAL); return ZephirTypes.ARROW; }
  "::"                 { yybegin(YYINITIAL); return ZephirTypes.DOUBLECOLON; }
  "."                  { yybegin(YYINITIAL); return ZephirTypes.DOT; }
  "+"                  { yybegin(YYINITIAL); return ZephirTypes.ADD; }
  "-"                  { yybegin(YYINITIAL); return ZephirTypes.SUB; }
  "*"                  { yybegin(YYINITIAL); return ZephirTypes.MUL; }
  "**"                 { yybegin(YYINITIAL); return ZephirTypes.EXP; }
  "/"                  { yybegin(YYINITIAL); return ZephirTypes.DIV; }
  "%"                  { yybegin(YYINITIAL); return ZephirTypes.MOD; }
  "++"                 { yybegin(YYINITIAL); return ZephirTypes.INCR; }
  "--"                 { yybegin(YYINITIAL); return ZephirTypes.DECR; }
  ":"                  { yybegin(YYINITIAL); return ZephirTypes.COLON; }
  ";"                  { yybegin(YYINITIAL); return ZephirTypes.DOTCOMMA; }
  ","                  { yybegin(YYINITIAL); return ZephirTypes.COMMA; }
  "?"                  { yybegin(YYINITIAL); return ZephirTypes.QUESTION; }

  {COMMENT}            { yybegin(YYINITIAL); return ZephirTypes.COMMENT; }
  {COMMENT_BLOCK}      { yybegin(YYINITIAL); return ZephirTypes.COMMENT_BLOCK; }
  {IDENTIFIER}         { yybegin(YYINITIAL); return ZephirTypes.IDENTIFIER; }
  {INTEGER}            { yybegin(YYINITIAL); return ZephirTypes.INTEGER; }
  {DOUBLE}             { yybegin(YYINITIAL); return ZephirTypes.DOUBLE; }
  {SCHAR}              { yybegin(YYINITIAL); return ZephirTypes.SCHAR; }
  {STRING}             { yybegin(YYINITIAL); return ZephirTypes.STRING; }
  {CBLOCK}             { yybegin(YYINITIAL); return ZephirTypes.CBLOCK; }

  .                    { yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}
