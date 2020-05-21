// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.stubs

import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.IStubFileElementType
import com.zephir.lang.core.ZephirLanguage
import com.zephir.lang.core.psi.ZephirFile

class ZephirStubFileElementType : IStubFileElementType<PsiFileStub<ZephirFile>>(ZephirLanguage)
