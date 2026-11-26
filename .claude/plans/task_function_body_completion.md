---
name: Completion in Top-Level Function Bodies
description: A top-level function body gets no statement keywords, type hints or variable completion
type: task
---

# Task: Completion in Top-Level Function Bodies

Statement keywords, type hints, local variables and built-in functions are offered only inside a method body
(`ZephirMethodBody`). The body of a top-level function (`ZephirFunctionDefinition`) gets none of them.

**File(s):** `src/main/kotlin/com/zephir/lang/core/completion/` (the method-scope, variables and built-in function
suggestors, and `ZephirCompletionScope.kt`), `src/test/kotlin/ZephirCompletionTest.kt`

- Treat a top-level function's code block like a method body for these suggestors; `this->` and `self::` stay
  method-only.
- Add completion tests inside a top-level function body: a statement keyword, a parameter, a local variable and a
  built-in function.
