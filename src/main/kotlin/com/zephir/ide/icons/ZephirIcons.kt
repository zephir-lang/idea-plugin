// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.icons

import javax.swing.Icon
import com.intellij.openapi.util.IconLoader

object ZephirIcons {
    /** Basic file icon, matching the rest of IntelliJ's file icons. */
    val FILE = getIcon("zephir.png")

    /**
     * Returns the absolute path of the given [icon].
     */
    private fun getIcon(icon: String): Icon {
        return IconLoader.getIcon("/icons/$icon")
    }
}
