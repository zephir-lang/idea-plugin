// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.zephir.ide.icons.ZephirIcons

object ZephirLanguage : Language("Zephir", "text/zephir", "text/x-zephir", "application/x-zephir")

object ZephirFileType : LanguageFileType(ZephirLanguage) {
    private const val EXTENSION = "zep"

    override fun getIcon() = ZephirIcons.FILE
    override fun getName() = "Zephir"
    override fun getDefaultExtension() = EXTENSION
    override fun getDescription() = "Zephir language file"
}
