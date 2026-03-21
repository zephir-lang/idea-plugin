# Zephir plugin for the IntelliJ Platform

[![Build Status][actions-badge]][actions link]

An IntelliJ plugin for editing [Zephir][zephir-lang] code. Provides syntax
highlighting, autocompletion, code folding, structure view, and live templates.

Zephir is a high level programming language that eases the creation and
maintainability of extensions for PHP. Zephir extensions are exported to C code
that can be compiled and optimized by major C compilers such as gcc/clang/vc++.
Functionality is exposed to the PHP language.

## Highlighted Features

- Syntax highlighting
- Syntax check support
- **Autocompletion** — local variables, class/interface names from the project, PHP built-in functions, and member access (`->`) for Zephir-typed variables
- **Structure View** — browse classes, interfaces, methods, properties, and constants via the IDE structure panel
- **Code Folding** — fold class/interface bodies, method blocks, inline C blocks (`%{ }%`), and multi-line block comments
- **Live Templates** — expand common Zephir snippets with a short abbreviation:

  | Abbreviation | Expands to                             |
  |--------------|----------------------------------------|
  | `cl`         | class definition                       |
  | `pubf`       | public function                        |
  | `protf`      | protected function                     |
  | `privf`      | private function                       |
  | `fore`       | `for key, value in collection` loop    |
  | `fori`       | `for value in collection` loop         |
  | `rev`        | `for value in reverse collection` loop |
  | `wh`         | while loop                             |
  | `dowhile`    | do-while loop                          |
  | `tc`         | try-catch block                        |

- **Rename Refactoring** — rename classes, interfaces, methods, properties, and constants across the file
- **Implementations Gutter** — see subclass and implementation counts next to class and interface declarations in the editor gutter
- **Go To Declaration** — Ctrl+click a class or interface name to jump to its declaration; qualified names resolve via namespace-to-directory mapping, and aliases navigate to their `use` statement

## Install

You can install the plugin from within the JetBrains IDE by going to
**Settings > Plugins > Marketplace** and then searching for "Zephir". After
installing the plugin, restart the IDE and then open your existing Zephir
project or create a new project.

## Contributing

You're encouraged to contribute to the plugin if you've found any issues or
missing functionality that you would want to see. Check out
[CONTRIBUTING.md](./CONTRIBUTING.md) to learn how to set up the project.

## Links

- [Zephir Language][zephir-lang]
- [Plugin page][plugin-page]

## License

Copyright (c) 2014-2026 Zephir Team.

Licensed under the [MIT](./LICENSE) License.

[actions link]: https://github.com/zephir-lang/idea-plugin/actions
[actions-badge]: https://github.com/zephir-lang/idea-plugin/workflows/build/badge.svg
[zephir-lang]: https://zephir-lang.com/en
[plugin-page]: http://plugins.jetbrains.com/plugin/7558
