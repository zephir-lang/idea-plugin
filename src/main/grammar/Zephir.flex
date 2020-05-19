package com.zephir.lang.core.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import static com.zephir.lang.core.psi.ZephirTypes.*;

%%

%{
  public ZephirLexer() {
    this(null);
  }
%}

%class ZephirLexer
%public
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

  "namespace"          { yybegin(YYINITIAL); return NAMESPACE; }
  "use"                { yybegin(YYINITIAL); return USE; }
  "as"                 { yybegin(YYINITIAL); return AS; }
  "interface"          { yybegin(YYINITIAL); return INTEFACE; }
  "class"              { yybegin(YYINITIAL); return CLASS; }
  "function"           { yybegin(YYINITIAL); return FUNCTION; }
  "fn"                 { yybegin(YYINITIAL); return FUNCTION; }
  "extends"            { yybegin(YYINITIAL); return EXTENDS; }
  "implements"         { yybegin(YYINITIAL); return IMPLEMENTS; }
  "abstract"           { yybegin(YYINITIAL); return ABSTRACT; }
  "final"              { yybegin(YYINITIAL); return FINAL; }
  "public"             { yybegin(YYINITIAL); return PUBLIC; }
  "protected"          { yybegin(YYINITIAL); return PROTECTED; }
  "private"            { yybegin(YYINITIAL); return PRIVATE; }
  "static"             { yybegin(YYINITIAL); return STATIC; }
  "inline"             { yybegin(YYINITIAL); return INLINE; }
  "internal"           { yybegin(YYINITIAL); return INTERNAL; }
  "deprecated"         { yybegin(YYINITIAL); return DEPRECATED; }
  "var"                { yybegin(YYINITIAL); return TYPE_VAR; }
  "void"               { yybegin(YYINITIAL); return TYPE_VOID; }
  "int"                { yybegin(YYINITIAL); return TYPE_INT; }
  "integer"            { yybegin(YYINITIAL); return TYPE_INT; }
  "uint"               { yybegin(YYINITIAL); return TYPE_UINT; }
  "long"               { yybegin(YYINITIAL); return TYPE_LONG; }
  "ulong"              { yybegin(YYINITIAL); return TYPE_ULONG; }
  "char"               { yybegin(YYINITIAL); return TYPE_CHAR; }
  "uchar"              { yybegin(YYINITIAL); return TYPE_UCHAR; }
  "double"             { yybegin(YYINITIAL); return TYPE_DOUBLE; }
  "float"              { yybegin(YYINITIAL); return TYPE_DOUBLE; }
  "bool"               { yybegin(YYINITIAL); return TYPE_BOOL; }
  "boolean"            { yybegin(YYINITIAL); return TYPE_BOOL; }
  "string"             { yybegin(YYINITIAL); return TYPE_STRING; }
  "array"              { yybegin(YYINITIAL); return TYPE_ARRAY; }
  "object"             { yybegin(YYINITIAL); return TYPE_OBJECT; }
  "callable"           { yybegin(YYINITIAL); return TYPE_CALLABLE; }
  "resource"           { yybegin(YYINITIAL); return TYPE_RESOURCE; }
  "null"               { yybegin(YYINITIAL); return NULL; }
  "false"              { yybegin(YYINITIAL); return FALSE; }
  "true"               { yybegin(YYINITIAL); return TRUE; }
  "let"                { yybegin(YYINITIAL); return LET; }
  "echo"               { yybegin(YYINITIAL); return ECHO; }
  "const"              { yybegin(YYINITIAL); return CONST; }
  "if"                 { yybegin(YYINITIAL); return IF; }
  "else"               { yybegin(YYINITIAL); return ELSE; }
  "elseif"             { yybegin(YYINITIAL); return ELSEIF; }
  "switch"             { yybegin(YYINITIAL); return SWITCH; }
  "case"               { yybegin(YYINITIAL); return CASE; }
  "default"            { yybegin(YYINITIAL); return DEFAULT; }
  "do"                 { yybegin(YYINITIAL); return DO; }
  "while"              { yybegin(YYINITIAL); return WHILE; }
  "for"                { yybegin(YYINITIAL); return FOR; }
  "loop"               { yybegin(YYINITIAL); return LOOP; }
  "reverse"            { yybegin(YYINITIAL); return REVERSE; }
  "break"              { yybegin(YYINITIAL); return BREAK; }
  "continue"           { yybegin(YYINITIAL); return CONTINUE; }
  "in"                 { yybegin(YYINITIAL); return IN; }
  "new"                { yybegin(YYINITIAL); return NEW; }
  "return"             { yybegin(YYINITIAL); return RETURN; }
  "require"            { yybegin(YYINITIAL); return REQUIRE; }
  "clone"              { yybegin(YYINITIAL); return CLONE; }
  "empty"              { yybegin(YYINITIAL); return EMPTY; }
  "typeof"             { yybegin(YYINITIAL); return TYPEOF; }
  "instanceof"         { yybegin(YYINITIAL); return INSTANCEOF; }
  "likely"             { yybegin(YYINITIAL); return LIKELY; }
  "unlikely"           { yybegin(YYINITIAL); return UNLIKELY; }
  "isset"              { yybegin(YYINITIAL); return ISSET; }
  "unset"              { yybegin(YYINITIAL); return UNSET; }
  "throw"              { yybegin(YYINITIAL); return THROW; }
  "fetch"              { yybegin(YYINITIAL); return FETCH; }
  "try"                { yybegin(YYINITIAL); return TRY; }
  "catch"              { yybegin(YYINITIAL); return CATCH; }
  "("                  { yybegin(YYINITIAL); return PARENTHESES_OPEN; }
  ")"                  { yybegin(YYINITIAL); return PARENTHESES_CLOSE; }
  "{"                  { yybegin(YYINITIAL); return BRACKET_OPEN; }
  "}"                  { yybegin(YYINITIAL); return BRACKET_CLOSE; }
  "["                  { yybegin(YYINITIAL); return SBRACKET_OPEN; }
  "]"                  { yybegin(YYINITIAL); return SBRACKET_CLOSE; }
  "@"                  { yybegin(YYINITIAL); return AT; }
  "!"                  { yybegin(YYINITIAL); return NOT; }
  "&&"                 { yybegin(YYINITIAL); return AND; }
  "||"                 { yybegin(YYINITIAL); return OR; }
  "&"                  { yybegin(YYINITIAL); return BITWISE_AND; }
  "|"                  { yybegin(YYINITIAL); return BITWISE_OR; }
  "^"                  { yybegin(YYINITIAL); return BITWISE_XOR; }
  "<<"                 { yybegin(YYINITIAL); return BITWISE_SHIFTLEFT; }
  ">>"                 { yybegin(YYINITIAL); return BITWISE_SHIFTRIGHT; }
  "="                  { yybegin(YYINITIAL); return ASSIGN; }
  "+="                 { yybegin(YYINITIAL); return ADDASSIGN; }
  "-="                 { yybegin(YYINITIAL); return SUBASSIGN; }
  "*="                 { yybegin(YYINITIAL); return MULASSIGN; }
  "**="                { yybegin(YYINITIAL); return EXPASSIGN; }
  "/="                 { yybegin(YYINITIAL); return DIVASSIGN; }
  "%="                 { yybegin(YYINITIAL); return MODASSIGN; }
  ".="                 { yybegin(YYINITIAL); return CONCATASSIGN; }
  "=="                 { yybegin(YYINITIAL); return EQUALS; }
  "!="                 { yybegin(YYINITIAL); return NOTEQUALS; }
  "==="                { yybegin(YYINITIAL); return IDENTICAL; }
  "!=="                { yybegin(YYINITIAL); return NOTIDENTICAL; }
  "<="                 { yybegin(YYINITIAL); return LESSEQUAL; }
  ">="                 { yybegin(YYINITIAL); return GREATEREQUAL; }
  "<"                  { yybegin(YYINITIAL); return LESS; }
  ">"                  { yybegin(YYINITIAL); return GREATER; }
  "->"                 { yybegin(YYINITIAL); return ARROW; }
  "::"                 { yybegin(YYINITIAL); return DOUBLECOLON; }
  "."                  { yybegin(YYINITIAL); return DOT; }
  "+"                  { yybegin(YYINITIAL); return ADD; }
  "-"                  { yybegin(YYINITIAL); return SUB; }
  "*"                  { yybegin(YYINITIAL); return MUL; }
  "**"                 { yybegin(YYINITIAL); return EXP; }
  "/"                  { yybegin(YYINITIAL); return DIV; }
  "%"                  { yybegin(YYINITIAL); return MOD; }
  "++"                 { yybegin(YYINITIAL); return INCR; }
  "--"                 { yybegin(YYINITIAL); return DECR; }
  ":"                  { yybegin(YYINITIAL); return COLON; }
  ";"                  { yybegin(YYINITIAL); return DOTCOMMA; }
  ","                  { yybegin(YYINITIAL); return COMMA; }
  "?"                  { yybegin(YYINITIAL); return QUESTION; }

  {COMMENT}            { yybegin(YYINITIAL); return COMMENT; }
  {COMMENT_BLOCK}      { yybegin(YYINITIAL); return COMMENT_BLOCK; }
  {IDENTIFIER}         { yybegin(YYINITIAL); return IDENTIFIER; }
  {INTEGER}            { yybegin(YYINITIAL); return INTEGER; }
  {DOUBLE}             { yybegin(YYINITIAL); return DOUBLE; }
  {SCHAR}              { yybegin(YYINITIAL); return SCHAR; }
  {STRING}             { yybegin(YYINITIAL); return STRING; }
  {CBLOCK}             { yybegin(YYINITIAL); return CBLOCK; }

  [^]                  { yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}
