// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion

class ZephirCompletionPriority {
    companion object {
        var METHOD_SCOPE_PRIORITY = 2.0
        var CLASS_METHOD_PRIORITY = 1.7
        var CLASS_CONSTS_PRIORITY = 1.6
        var CLASS_PROPERTY_PRIORITY = 1.5
    }
}
