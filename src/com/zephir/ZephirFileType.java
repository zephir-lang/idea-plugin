// Copyright (c) 2014-2019 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

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
