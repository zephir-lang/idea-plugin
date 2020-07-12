#!/usr/bin/env bash

# Copyright (c) 2014-2020 Zephir Team
#
# This source file is subject the MIT license, that is bundled with
# this package in the file LICENSE, and is available through the
# world-wide-web at the following url:
#
# https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

# -e  Exit immediately if a command exits with a non-zero status.
# -u  Treat unset variables as an error when substituting.
set -eu

COPYRIGHT_NOTICE=$(cat <<-EOF
// Copyright \\(c\\) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
EOF
)

COPYRIGHT_NOTICE=$(echo "$COPYRIGHT_NOTICE" | tr '\r\n' ' ')

NO_LICENSE=$(find . -type f \( -iname '*.kt' -o -iname '*.kts' -o -iname '*.bnf' -o -iname '*.flex' \) -print0 | \
  xargs -0 pcregrep -L -M "${COPYRIGHT_NOTICE// /\\s}" || true)

if [ -n "$NO_LICENSE" ]; then
    echo "Files without license notice (or with a wrong notice format) are:"
    echo "$NO_LICENSE"
    exit 1
else
    echo "License OK!"
fi
