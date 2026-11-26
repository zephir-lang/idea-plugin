---
name: Constant Completion Without a Default
description: Method-scope completion throws on a class constant declared without a value
type: task
---

# Task: Constant Completion Without a Default

`ZephirMethodScopeCompletionSuggestor` builds `self::` constant suggestions with a `!!` on the constant's default
value. The grammar allows a constant without one, so completion throws a NullPointerException in a class that declares
one.

**File(s):** `src/main/kotlin/com/zephir/lang/core/completion/suggestors/ZephirMethodScopeCompletionSuggestor.kt`,
`src/test/kotlin/`

- Suggest the constant without a type or value hint when it has no default.
- Add a completion test with a constant that has no default.
