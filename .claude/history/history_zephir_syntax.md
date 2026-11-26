---
name: Zephir Syntax Support History
description: Changes to the plugin's lexer, grammar and the IDE features built on them
type: history
---

# Zephir Syntax Support History

## 2026-11-25 — Zephir 1.10 syntax, error recovery and trait IDE support

Branch `feature/zephir-1.10-syntax`. Brought the lexer and grammar up to what Zephir 1.10.0 / php-zephir-parser 2.12.0
accept, added error recovery, and extended the IDE features to traits and top-level functions.

**Delivered**
- Lexer: case-insensitive keywords (`%caseless`); tokens `trait`, `readonly`, `yield`, `require_once`, `#[`, `=>`,
  `..`, the bit-assign operators, `~` and `~"..."`.
- Grammar: free-order top level with optional namespace and top-level functions; traits and `use` in bodies;
  free-order member modifiers; typed, nullable, union and readonly properties; shortcuts as identifiers; union, `const`,
  by-reference and `...&x` parameters; return types with `false`, `<Name[]>` and `this`; attributes; expression
  defaults; `yield`; `require_once`; destructuring; closure return types and `name => expr` arrows; named arguments;
  `::class` and late-static access; `new static`; `instanceof static/self/parent`; ranges, `?:`; empty statements;
  method calls on `new X()` and on an array access.
- Error recovery at top-level, member, statement and sub-rule level, with errors reported at the bad token; a Java
  parser util drops `<`/`>` from the pairs recovery balances.
- IDE: highlighting and an attribute colour (annotator); completion scopes (file, class header, class/trait body, code
  block); trait members, trait names and destructured variables in completion; traits and functions in the structure
  view, folding, navigation, a "Used By" gutter marker, documentation, and a Zephir Trait file template.
- 92 tests: 52 parser fixtures (24 of them error or recovery fixtures) and 40 editor tests.

**Decisions**
- The grammar follows the parser, not the compiler: a missing namespace, unknown shortcut names and readonly rules are
  not parse errors.
- Stricter than before where Zephir rejects a form: `void`/`<$x>` outside a whole return type, `&...x`, `?T` on
  parameters, `<Cls[]>` outside returns, methods with no modifier.
- "Syntax check" became error recovery: the platform already highlighted parse errors, and no annotator was needed for
  that.
- Methods and abstract methods merged into one rule pinned on the name, so errors land on the bad token; chosen over
  keeping valid trees unchanged with errors reported at the member start. Abstract methods are listed in file order in
  the structure view.
- Method calls keep two PSI shapes (`CallExpr` chains and `MethodCallExpr`) because one shape would have rewritten
  every existing call tree.
- Toolchain (Grammar-Kit 2022.3.2, IntelliJ Platform Gradle plugin 2.13.1, IDEA 2024.3) left unchanged; changelog
  entries under Unreleased, version 0.6.0.
- Left out and recorded as tasks: expression precedence, property access on any receiver, class name references /
  Find Usages, completion in top-level function bodies, header completion by position, the `\`/`$` tokens, constant
  completion without a default, the commenter's doc comment check, trait member completion in using classes, and the
  CONTRIBUTING issue link.
