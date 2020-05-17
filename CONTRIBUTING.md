# Contributing to Zephir plugin for the IntelliJ Platform

Zephir plugin for the IntelliJ Platform is an open source project and a
volunteer effort. This project welcomes contribution from everyone. The
following summarizes the process for contributing changes, and some
guidelines on how to set up the project. Please take a moment to review
this document in order to make the contribution process easy and effective
for everyone involved.

## Prerequisites

http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/prerequisites.html

## Getting started

### Clone

```shell script
$ git clone https://github.com/zephir-lang/idea-plugin.git
$ cd intellij-rust
```

### Building & Running

1. Go to `src/com/zephir/Zephir.bnf` file and run **Generate parser code** through context menu
2. Go to `src/com/zephir/lexer/Zephir.flex` and run **Run JFlex generator** through context menu.
   You will need to specify a directory to download `lib/jflex-*.jar` file (outside the project)
3. Specify `gen` folder as _Generated Sources Root_:
   **File** -> **Project Structure...** -> **Modules** -> **Sources**
4. Build project: **Build** -> **Build Project**
5. Prepare module: **Build** -> **Prepare Plugin Module 'intellij.zephir' For Deployment**
