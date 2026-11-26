---
name: Header Completion by Position
description: Class header completion offers extends and implements wherever the caret is, including after implements
type: task
---

# Task: Header Completion by Position

`ZephirHeaderKeywordsSuggestor` offers `extends` and `implements` anywhere in a class header and `extends` anywhere in
an interface header. After `implements A, B` neither keyword is valid, and a keyword already present is never valid
again.

**File(s):** `src/main/kotlin/com/zephir/lang/core/completion/suggestors/ZephirHeaderKeywordsSuggestor.kt`,
`src/test/kotlin/ZephirCompletionTest.kt`

- Offer only keywords not already in the header, and nothing after `implements` (or after an interface's `extends`).
- Add completion tests for `class Foo extends Bar implements A, B <caret>`, `class Foo implements A <caret>` and
  `interface Foo extends A <caret>`.
