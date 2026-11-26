---
name: Trait Member Completion in Using Classes
description: Offer the members of used traits in this-> and self:: completion inside a class
type: task
---

# Task: Trait Member Completion in Using Classes

Inside a class, `this->` and `self::` completion offers only the class's own members. Members of the traits named in
its `use` statements are missing.

**File(s):** `src/main/kotlin/com/zephir/lang/core/completion/suggestors/ZephirMethodScopeCompletionSuggestor.kt`,
`src/test/kotlin/`

- Resolve each trait named in the class's `use` statements, in the same file or elsewhere in the project, and add its
  members to the suggestions, including members of traits those traits use.
- Guard against a trait cycle.
- Add completion tests for a trait in the same file, a trait in another file and a trait using a trait.
