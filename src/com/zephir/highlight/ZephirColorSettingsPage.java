package com.zephir.highlight;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.zephir.ZephirIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

/**
 * @author Nikita Gusakov
 */
public class ZephirColorSettingsPage implements ColorSettingsPage {
    @Nullable
    @Override
    public Icon getIcon() {
        return ZephirIcons.ZEPHIR;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new ZephirSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "namespace Zephir;\n" +
                "\n" +
                "class Foo extends Bar implements Baz\n" +
                "{\n" +
                "    const FOO_BAR = 30 * 24 * 60;\n" +
                "\n" +
                "    /**\n" +
                "     * @var int\n" +
                "     */\n" +
                "    public num;\n" +
                "\n" +
                "    /**\n" +
                "     * @var string\n" +
                "     */\n" +
                "    static private str;\n" +
                "\n" +
                "    public function __construct(int num, string str, array arr)\n" +
                "    {\n" +
                "        var abc;\n" +
                "        if fetch abc, arr[str] {\n" +
                "            let this->num = abc;\n" +
                "        } elseif isset arr[num] {\n" +
                "            let self::str = str;\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return new AttributesDescriptor[]{
                new AttributesDescriptor("Keyword", ZephirSyntaxHighlighter.KEYWORD),
                new AttributesDescriptor("Identifier", ZephirSyntaxHighlighter.IDENTIFIER),
                new AttributesDescriptor("Comment", ZephirSyntaxHighlighter.LINE_COMMENT),
                new AttributesDescriptor("DocBlock", ZephirSyntaxHighlighter.DOC_COMMENT),
                new AttributesDescriptor("String", ZephirSyntaxHighlighter.STRING),
                new AttributesDescriptor("Number", ZephirSyntaxHighlighter.NUMBER),
                new AttributesDescriptor("Operator", ZephirSyntaxHighlighter.OPERATOR),
                new AttributesDescriptor("Variable type", ZephirSyntaxHighlighter.ATTRIBUTE),
        };
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Zephir";
    }
}
