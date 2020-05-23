# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com)
and this project adheres to [Semantic Versioning](http://semver.org).

## [Unreleased][Unreleased]
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

[Unreleased]: https://github.com/zephir-lang/idea-plugin/compare/0.4.0...HEAD
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
