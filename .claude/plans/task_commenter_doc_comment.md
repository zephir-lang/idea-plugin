---
name: Commenter Doc Comment Check
description: ZephirCommenter never recognises a doc comment and can throw
type: task
---

# Task: Commenter Doc Comment Check

`ZephirCommenter.isDocumentationComment` compares the element's token type against `ZephirTypes.CODE_BLOCK`, a rule
type, not the doc comment token, so it always returns false. It also uses `element!!`, which throws when no element is
passed.

**File(s):** `src/main/kotlin/com/zephir/ide/commenter/ZephirCommenter.kt`, `src/test/kotlin/`

- Compare against the doc comment token and return false for a missing element.
- Add a test that a `/** */` comment is recognised and a `/* */` comment is not.
