/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * This source file is subject the MIT license, that is bundled with
 * this package in the file LICENSE, and is available through the
 * world-wide-web at the following url:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

package com.zephir;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class ZephirFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(ZephirFileType.INSTANCE);
    }
}
