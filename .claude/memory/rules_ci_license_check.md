---
name: Licence Check Needs pcregrep
description: .ci/check-license.sh prints "License OK!" and checks nothing when pcregrep is missing
type: rules
---

`.ci/check-license.sh` pipes the source file list into `xargs pcregrep … || true`. When `pcregrep` is not installed
(Homebrew `pcre`), the error is swallowed and the script still prints "License OK!", so it proves nothing. Install
`pcregrep`, or check the MIT header by eye on every `.kt`, `.kts`, `.java`, `.bnf` and `.flex` file outside `gen/`.
