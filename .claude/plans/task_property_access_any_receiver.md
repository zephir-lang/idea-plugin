---
name: Property and Array Access on Any Receiver
description: Property access and repeated array access fail after a call, a new expression or an array access
type: task
---

# Task: Property and Array Access on Any Receiver

The parser takes any expression before `->` for a property access (php-zephir-parser `parser/zephir.lemon`, the
`xx_common_expr ARROW IDENTIFIER` productions and their `{expr}` / `{"name"}` forms). The plugin accepts a property
access only on a variable, and a single `[...]` after a call:

- `new X()->p` and `foo()[0]->p` report `'(' expected`; `(new X())->p` works.
- `foo()[0][1]` and `new X()->y()[0][1]` fail at the second `[`.

**File(s):** `src/main/grammar/Zephir.bnf`, `src/test/testData/parser/`, `src/test/kotlin/ZephirParserTest.kt`

- Accept property access, including the `->{expr}` and `->{"name"}` forms, on any expression, and any number of
  array accesses after a call or a method call.
- Add a fixture covering each form as a statement and in a `let`; every other expected tree stays byte-identical.
