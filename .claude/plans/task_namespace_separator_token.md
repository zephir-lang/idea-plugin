---
name: Namespace Separator and Dollar Tokens
description: `\` and `$` have no lexer rules, so they lex as bad characters that the grammar matches by text
type: task
---

# Task: Namespace Separator and Dollar Tokens

`Zephir.flex` has no rule for the namespace separator `\` or for `$`, so both lex as BAD_CHARACTER. `Zephir.bnf`
matches `'\'` and `'$'` by their text, so qualified names and `$`-prefixed names parse, but every parse tree holding
them shows bad-character tokens, and they would be flagged if bad characters were ever highlighted.

**File(s):** `src/main/grammar/Zephir.flex`, `src/main/grammar/Zephir.bnf` (tokens block), `src/test/testData/parser/*.txt`

- Add a token for each, and use the tokens in the rules that match the text today.
- Every expected parse tree holding a qualified name changes; regenerate them and check the differences are the token
  type only.
