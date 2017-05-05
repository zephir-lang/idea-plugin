Zephir IntelliJ IDEA plugin
==========================

Plugin page: http://plugins.jetbrains.com/plugin/7558

`Start develop the Zephir plugin`

1. Install next plugins for Intellij 

https://plugins.jetbrains.com/plugin/6606-grammar-kit
https://plugins.jetbrains.com/plugin/227-psiviewer


2. Go to Zephir.bnf file and run "Generate parser code" through context menu
3. Go to src/com/zephir/Zephir.flex and run "Run JFlex generator" through context menu


4. Run the plugin by Menu->Run->Run

To automatically detect "Zephir plugin" in the debugged IDE, uncomment  next lines in the `resources/META-INF/plugin.xml`

<!--<lang.parserDefinition language="Zephir" implementationClass="com.zephir.ZephirParserDefinition"/>-->