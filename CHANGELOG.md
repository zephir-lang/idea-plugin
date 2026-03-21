# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com)
and this project adheres to [Semantic Versioning](http://semver.org).

## [0.5.1][0.5.1] - 2026-03-20
### Changed
- Replaced internal API override of `IElementType.getDebugName()` in `ZephirStubFileElementType` with the public `IStubFileElementType(String, Language)` constructor parameter

## [0.5.0][0.5.0] - 2026-03-20
### Fixed
- Replaced `IElementType.getDebugName()` override in `ZephirStubFileElementType` with `toString()` to eliminate `@ApiStatus.Internal` API usage flagged by Plugin Verifier

### Added
- Enhanced autocompletion for Zephir code:
  - Local variable names — suggests variables declared with `let`, type-annotated declarations (`var`/`string`/etc.), and `for` loop iteration variables
  - Class and interface names — suggests all class and interface names found in the project's `.zep` files
  - PHP built-in functions — suggests ~80 common PHP built-in functions in method scope, auto-inserting `()` with the cursor positioned inside
  - Member access (`->`) — when a variable is typed as a Zephir class or interface (e.g. `<AdapterInterface> adapter`), typing `adapter->` suggests that type's methods and properties from the project's `.zep` files
  - Concrete class methods now included in `this->` completion (previously only abstract methods were suggested); selected methods auto-insert `()` with cursor inside
- Added Documentation functionality (**Ctrl+Q** and on **hover**)
- Added **Go To Declaration** (`Ctrl+click`) for class and interface references:
- Qualified names (`Phalcon\Storage\Adapter\Libmemcached`) resolve via namespace-to-directory mapping, preventing ambiguous matches when multiple classes share the same short name
- Unqualified names fall back to a full project scan
- Alias names (`use Foo as Bar` → clicking `Bar`) navigate to the `use` statement so the real class path can be followed with a second Ctrl+click
- Added `docker-compose.yml` with a persistent Gradle cache volume for reproducible local builds
- Added Structure View (`ZephirStructureViewFactory`, `ZephirStructureViewElement`, `ZephirStructureViewModel`) showing classes, interfaces, methods, properties, and constants in the IDE structure panel
- Added Code Folding (`ZephirFoldingBuilder`) for class/interface bodies, method code blocks, inline C blocks, and multi-line block comments
- Added Live Templates (`Zephir.xml`) with abbreviations: `cl` (class), `pubf`, `protf`, `privf` (function stubs), `fore`, `fori`, `rev` (for loops), `wh` (while), `dowhile` (do-while), `tc` (try-catch)
- Added `ZephirTemplateContext` so live templates are only active inside `.zep` files
- Added Implementations Gutter (`ZephirLineMarkerProvider`) showing subclass and implementation counts next to class and interface declarations
- Added `ZephirNamedElement` interface, `ZephirNamedElementMixin`, and `ZephirIdMixin` for rename refactoring support
- Added `ZephirElementFactory` to create PSI elements programmatically
- Added `ZephirRefactoringSupportProvider` to enable inline rename for named elements
- Added parser tests for `do...while` loop and `mixed` type usage

### Changed
- Requires IntelliJ Platform 2024.3 or newer (minimum build `243`)
- Gradle wrapper updated from 6.5.1 to 9.0.0
- Migrated from `org.jetbrains.intellij` 0.4.21 to `org.jetbrains.intellij.platform` 2.13.1
- `org.jetbrains.grammarkit` plugin updated from 2020.1 to 2022.3.2
- Kotlin JVM plugin updated from 1.3.72 to 2.1.0
- Replaced `jcenter()` repository with `mavenCentral()`
- JVM toolchain updated from 1.8 to 21
- GitHub Actions updated to Node.js 24 compatible versions (`checkout@v6`, `setup-java@v5`, `cache@v5`, `upload-artifact@v7`)
- GitHub Actions build updated to use JDK 21
- Grammar: added `mixed`, `object`, `callable`, `resource` as recognized type keywords
- Grammar: added `mixed` type to type hints in method scope completion
- Grammar: added `do...while` loop statement (`do { } while expr;`)
- Grammar: fixed `INTEFACE` token name typo — corrected to `INTERFACE`
- Grammar: added `mixin`/`implements` attributes to `Id`, class, interface, method, property, and constant definition rules
- Replaced deprecated `IconLoader.getIcon(String)` with `IconLoader.getIcon(String, ClassLoader)`
- Replaced deprecated `LOG` static field with `thisLogger()` in `ZephirCreateFileAction`
- Fixed `ZephirStubFileElementType` to provide unique `externalId` and `debugName` to prevent platform exceptions
- Updated copyright year to 2026 across all source files

## [0.4.0][0.4.0] - 2020-05-24
### Added
- Amended Color Scheme configuration
- Introduced recognition of angle brackets as paired ones
- Added ability to create an empty Zephir File

### Changed
- Migrated build to Gradle
- Rewrote plugin from Java to Kotlin
- Redesigned logic, determining when a closed brace/bracket should be inserted after an opened one

### Fixed
- Corrected `New > Zephir File` dialog to use project's directory as a namespace

### Removed
- Drop support of IDE's versions below `191`

## [0.3.6][0.3.6] - 2020-05-18
### Changed
- Updated file icons
- Corrected `change-notes` section to store changes only for the latest version of the plugin

## [0.3.5][0.3.5] - 2020-05-13
### Removed
- Drop support of IDE's versions below `182`

## [0.3.4][0.3.4] - 2020-05-12
### Fixed
- Replaced usage of deprecated API by new one

## [0.3.3][0.3.3] - 2019-09-07
### Fixed
- Fixed recognizing strings with regular expressions

## [0.3.2][0.3.2] - 2017-12-27
### Added
- Completion list now shows members of class

### Changed
- Improved syntax support

## [0.3.1][0.3.1] - 2017-05-18
### Fixed
- Fixed many bugs with syntax recognition
- Fixed extra space in completion for method params

## [0.3.0][0.3.0] - 2017-05-12
### Added
- Added few words to highlight

### Changed
- Improved completion

### Fixed
- Fixed many bugs with syntax recognition

## [0.2.5][0.2.5] - 2017-05-06
### Fixed
- Fixed `switch` keyword detection

## [0.2.4][0.2.4] - 2014-09-22
### Added
- Added brace matching support

## [0.2.3][0.2.3] - 2014-09-20
### Fixed
- Fixed build

## [0.2.2][0.2.2] - 2014-09-15
### Added
- Added color settings page

### Changed
- Improve highlighter

## [0.2.1][0.2.2] - 2014-09-15
### Changed
- Disabled "New Zephir class" dialog

## [0.2][0.2] - 2014-09-15
### Added
- Added lexer and simple syntax highlighter

## 0.1 - 2014-09-08
### Added
- Initial release

[Unreleased]: https://github.com/zephir-lang/idea-plugin/compare/0.5.1...HEAD
[0.5.1]: https://github.com/zephir-lang/idea-plugin/compare/0.5.0...0.5.1
[0.5.0]: https://github.com/zephir-lang/idea-plugin/compare/0.4.0...0.5.0
[0.4.0]: https://github.com/zephir-lang/idea-plugin/compare/0.3.6...0.4.0
[0.3.6]: https://github.com/zephir-lang/idea-plugin/compare/0.3.5...0.3.6
[0.3.5]: https://github.com/zephir-lang/idea-plugin/compare/0.3.4...0.3.5
[0.3.4]: https://github.com/zephir-lang/idea-plugin/compare/0.3.3...0.3.4
[0.3.3]: https://github.com/zephir-lang/idea-plugin/compare/0.3.2...0.3.3
[0.3.2]: https://github.com/zephir-lang/idea-plugin/compare/0.3.1...0.3.2
[0.3.1]: https://github.com/zephir-lang/idea-plugin/compare/0.3.0...0.3.1
[0.3.0]: https://github.com/zephir-lang/idea-plugin/compare/0.2.5...0.3.0
[0.2.5]: https://github.com/zephir-lang/idea-plugin/compare/0.2.4...0.2.5
[0.2.4]: https://github.com/zephir-lang/idea-plugin/compare/0.2.3...0.2.4
[0.2.3]: https://github.com/zephir-lang/idea-plugin/compare/0.2.2...0.2.3
[0.2.2]: https://github.com/zephir-lang/idea-plugin/compare/0.2.1...0.2.2
[0.2.1]: https://github.com/zephir-lang/idea-plugin/compare/0.2...0.2.1
[0.2]: https://github.com/zephir-lang/idea-plugin/compare/0.1...0.2
