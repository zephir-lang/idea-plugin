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

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Nikita Gusakov
 */
public class ZephirFileType extends LanguageFileType {
    public static final String DEFAULT_EXTENSION = "zep";
    public static final LanguageFileType INSTANCE = new ZephirFileType();

    private ZephirFileType() {
        super(ZephirLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return ZephirLanguage.LANGUAGE_NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return ZephirLanguage.LANGUAGE_NAME;
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return ZephirIcons.ZEPHIR;
    }
}
