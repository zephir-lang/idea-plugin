// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.color

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.zephir.ide.highlight.ZephirSyntaxHighlighter
import com.zephir.ide.icons.ZephirIcons

class ZephirColorSettingsPage : ColorSettingsPage {
    private val attributes = ZephirColor.values().map {
        it.attributesDescriptor
    }.toTypedArray()

    override fun getHighlighter() = ZephirSyntaxHighlighter()
    override fun getIcon() = ZephirIcons.FILE
    override fun getAttributeDescriptors() = attributes
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
    override fun getDisplayName() = "Zephir"
    override fun getDemoText() = demoCodeText

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> {
        // TODO(serghei): Implement me
        return mutableMapOf()
    }
}

private const val demoCodeText = """
%{
// top statement before namespace, add to after headers
#define MAX_FACTOR 10
}%

namespace Acme;

%{
// top statement before class, add to after
// headers test include .h
#include "kernel/require.h"
}%

// a single line comment
use Acme\Some\Buz;

class Foo extends Bar implements Baz, Buz
{
    const FOO_BAR = 30 * 24 * 60;

    /**
     * @var int
     */
    public num;

    /** @var string */
    static private str;

    public function __construct(int num, string str, array arr)
    {
        var abc;
        int a = 0;
        string s = "test";
        char c = 'A';

        %{
            a = MAX_FACTOR;
        %}

        if fetch abc, arr[str] {
            let this->num = [abc: a, c: c];
        } elseif isset arr[num] {
            let self::str = [str: a, c: c];
        }
    }
    
    private function calc(
        <Arithmetic> l,
        <Arithmetic> r,
        <Bitwise> op
    ) -> <Result> | null {
        return <Result> op->calc(l, r);
    }
}
"""
