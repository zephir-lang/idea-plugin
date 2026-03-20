// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.template

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import com.zephir.lang.core.psi.ZephirFile

class ZephirTemplateContext : TemplateContextType("Zephir") {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean =
        templateActionContext.file is ZephirFile
}
