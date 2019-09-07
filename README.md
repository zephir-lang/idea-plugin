# Zephir IntelliJ IDEA plugin

## Getting started develop plugin

### Prerequisites

http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/prerequisites.html

### Build

1. Go to `src/com/zephir/Zephir.bnf` file and run **Generate parser code** through context menu
2. Go to `src/com/zephir/lexer/Zephir.flex` and run **Run JFlex generator** through context menu.
   You will need to specify a directory to download `lib/jflex-*.jar` file (outside the project)
3. Specify `gen` folder as _Generated Sources Root_:
   **File** -> **Project Structure...** -> **Modules** -> **Sources**
4. Build project: **Build** -> **Build Project**
5. Prepare module: **Build** -> **Prepare Plugin Module 'intellij.zephir' For Deployment**

## Links

- [Zephir Language](https://docs.zephir-lang.com)
- [Plugin page](http://plugins.jetbrains.com/plugin/7558)

## License

Copyright (c) 2014-2019 Zephir Team.

Licensed under the [MIT](LICENSE) License.
