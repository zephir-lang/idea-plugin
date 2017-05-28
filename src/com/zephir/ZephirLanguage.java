/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * Licensed under the MIT License (MIT);
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

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
