// Copyright (c) 2014-2019 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.Nullable;

public class ZephirLanguage extends Language {
    public static final String LANGUAGE_NAME = "Zephir";
    public static final Language INSTANCE = new ZephirLanguage();

    private ZephirLanguage() {
        super(LANGUAGE_NAME);
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }

    @Nullable
    @Override
    public LanguageFileType getAssociatedFileType() {
        return ZephirFileType.INSTANCE;
    }
}
