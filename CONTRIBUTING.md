# Contributing to Zephir plugin for the IntelliJ Platform

Zephir plugin for the IntelliJ Platform is an open source project and a
volunteer effort. This project welcomes contribution from everyone. The
following summarizes the process for contributing changes, and some
guidelines on how to set up the project. Please take a moment to review
this document in order to make the contribution process easy and effective
for everyone involved.

## Prerequisites

### Kotlin

We use [Kotlin][kotlin] language for the plugin. If you can program in Java,
you should be able to read and write Kotlin code right away. Kotlin is deeply
similar to Java, but has less verbose syntax and better safety. It also shares
some characteristics with Rust: type inference, immutability by default, no
null pointers (apart from those that come from Java).

If you are unsure how to implement something in Kotlin, ask a question in our
[Discord][discord] (use `#editorsupport` channel), send a PR in Java, or use
**Convert Java to Kotlin** action in the IDE.

Feel free to ask question or make suggestions in our [issue tracker][issues].

### Plugins

You might want to install the following plugins:

- [Grammar-Kit][g-kit] to get highlighting for the files with BNFish grammar
- [PsiViewer][psi-view] to view the AST of Zephir files right in the IDE
- [Kotlin][kotlin-plugin] to enable Kotlin support for your IDE
- Plugin DevKit (built-in, just make sure you've enabled it)

## Getting started

### Clone

```shell script
$ git clone https://github.com/zephir-lang/idea-plugin.git
$ cd idea-plugin
```

### Building & Running

Run `./gradlew runIde` run configuration to build and launch development IDE
with the plugin.

We use gradle with [gradle-intellij][gradle-intellij] plugin to build the
plugin. It comes with a wrapper script (`gradlew` in the root of the
repository)  which downloads appropriate version of gradle automatically as
long as you have JDK installed.  

Common Gradle tasks are:

- `./gradlew build` -- Fully build plugin and create an archive at
  `build/distributions` which can be installed into IntelliJ IDEA via
  `Install plugin from disk...` action found in `Settings > Plugins`
- `./gradlew runIde` - Runs Intellij IDEA with installed plugin. Break points
  works like a charm.
- `./gradlew test` - Runs the unit tests. To run gradle with single class and
  method use something like this: `gradle test --tests com.zephir.ns.Class`

### Development in IntelliJ IDEA

You can get the latest IntelliJ IDEA Community Edition [here][idea], it is
free. Import the plugin project as you would do with any other gradle based
project. For example, <kbd>Ctrl + Shift + A</kbd>,
`Import Project from Existing Sources...` and select `build.gradle.kts` from
the root directory of the plugin.

## Contributing

To find a problem to work on, look for [`help wanted`][help] issues on Github,
or, even better, try to fix a problem you face yourself when using the plugin.

Work in progress pull requests are very welcome! It is also a great way to ask
questions.

### Code style

Please use **reformat code** action to maintain consistent style. Pay attention
to IDEA's warning and suggestions, and try to keep the code green. If you are
sure that the warning is false positive, use an annotation to suppress it.

Try to avoid copy-paste and boilerplate as much as possible. For example,
proactively use `?:` to deal with nullable values.

If you add a new file, please make sure that it contains a license preamble, as
all other files do.

[kotlin]: https://kotlinlang.org
[kotlin-plugin]: https://plugins.jetbrains.com/plugin/6954-kotlin
[discord]: https://discord.gg/PNFsSsr
[issues]: https://github.com/zephir-lang/zephir-mode/issues
[gradle-intellij]: https://github.com/JetBrains/gradle-intellij-plugin
[idea]: https://www.jetbrains.com/idea/download/
[g-kit]: https://plugins.jetbrains.com/plugin/6606-grammar-kit
[psi-view]: https://plugins.jetbrains.com/plugin/227-psiviewer
[help]: https://github.com/zephir-lang/idea-plugin/labels/help%20wanted
