---
name: Expression Precedence
description: Several operators the plugin parses group differently from the Zephir parser's precedence table
type: task
---

# Task: Expression Precedence

The `Expr` choice in `Zephir.bnf` sets operator priority by the order of its alternatives, lowest first. Several
operators group differently from the precedence table in php-zephir-parser's `parser/zephir.lemon`:

- `UnaryExpr` has the lowest priority, so `!a && b` parses as `!(a && b)`. The parser binds `!`, unary `+` and unary
  `-` tightly.
- `MathExpr` is one flat level, so `a + b * 2` parses as `(a + b) * 2`. The parser puts `*`, `/` and `%` above `+`,
  `-` and `.`.
- `InstanceofExpr` takes a full expression on its right, so `a instanceof self && b instanceof parent` parses as
  `a instanceof (self && (b instanceof parent))`. The parser takes a class reference there.
- `TernaryExpr` is left-associative; the parser declares `?` right-associative.
- `InstanceofExpr` takes only a variable on its left, so `a->y() instanceof Z` and `new X()->y() instanceof Z` are
  errors. The parser takes any expression there.
- `CloneExpr` takes only a variable, so `clone foo()->bar()` parses as `(clone foo)()->bar()`. The parser reads
  `clone (foo()->bar())`.

**File(s):** `src/main/grammar/Zephir.bnf`, `src/test/testData/parser/`, `src/test/kotlin/ZephirParserTest.kt`

- Reorder or split the `Expr` alternatives to follow the parser's precedence and associativity.
- Add a `Precedence.zep` fixture whose expected tree checks the grouping of each case above, plus the cases the
  grammar already gets right (`x => a ? b : c`, `!a instanceof static`, `~a & b`, `a ?: b ?: c`, ranges against `+`).
- Expected trees of existing fixtures holding these operators change; check each difference is the grouping only.
