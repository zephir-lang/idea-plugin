---
name: Class Name References
description: Contributed references never attach to qualified names, so rename and Find Usages miss every usage
type: task
---

# Task: Class Name References

`ZephirReferenceContributor` registers `ZephirClassReference` on `ZephirComplexId`, but the generated
`ZephirComplexIdImpl` extends `ASTWrapperPsiElement`, which is not a `ContributedReferenceHost`, so `getReferences()`
never consults the contributor. Ctrl+click works through `ZephirGotoDeclarationHandler`; everything that needs a
reference does not:

- renaming a class, interface or trait updates only its declaration, not its usages;
- Find Usages and usage highlighting find nothing; no `findUsagesProvider` is registered.

**File(s):** `src/main/grammar/Zephir.bnf` (a mixin on `ComplexId`), `src/main/kotlin/com/zephir/lang/core/psi/`,
`src/main/resources/META-INF/plugin.xml`, `src/test/kotlin/`

- Give `ComplexId` a mixin that implements `ContributedReferenceHost` and returns
  `ReferenceProvidersRegistry.getReferencesFromProviders(this)` from `getReferences()`.
- Register a `findUsagesProvider` for Zephir.
- The README's and the `plugin.xml` description's "Rename Refactoring … across the file" line claims more than rename
  does; correct it to match once references work.
- Add tests: `findReferenceAt` on a `use` name resolves; renaming a trait updates the `use` statements naming it; Find
  Usages on a class lists its `extends`, `implements`, `use` and `new` usages.
