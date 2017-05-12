# Zephir IntelliJ IDEA plugin

Plugin page: http://plugins.jetbrains.com/plugin/7558

## Getting started

1. Install next plugins for Intellij
  * https://plugins.jetbrains.com/plugin/6606-grammar-kit
  * https://plugins.jetbrains.com/plugin/227-psiviewer
2. Go to `Zephir.bnf` file and run "Generate parser code" through context menu
3. Go to `src/com/zephir/Zephir.flex` and run "Run JFlex generator" through context menu
4. Run the plugin by Menu -> Run -> Run

To automatically detect "Zephir plugin" in the debugged IDE, uncomment  next lines in the `resources/META-INF/plugin.xml`

```xml
<!--<lang.parserDefinition language="Zephir" implementationClass="com.zephir.ZephirParserDefinition"/>-->
```

## Links

+ [**`phalcon/zephir`**](https://github.com/phalcon/zephir) - Zephir is a compiled high level language aimed to the creation of C-extensions for PHP
+ [**`phalcon/php-zephir-parser`**](https://github.com/phalcon/php-zephir-parser) - The Zephir Parser delivered as a C extension for the PHP language
+ [**`phalcon/zephir-docs`**](https://github.com/phalcon/zephir-docs) - Zephir Documentation and website
+ [**`phalcon/zephir-sublime`**](https://github.com/phalcon/zephir-sublime) - Sublime Text syntax highlighting for for Zephir
+ [**`phalcon/cphalcon`**](https://github.com/phalcon/cphalcon) - High performance, full-stack PHP framework written in Zephir/C

## License

Copyright (c) 2014-2017 Zephir Team.

Licensed under the [MIT](LICENSE)
