// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion.suggestors

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.lang.core.completion.ZephirCompletionSuggestor
import com.zephir.lang.core.psi.ZephirMethodBody

object ZephirBuiltinFunctionSuggestor : ZephirCompletionSuggestor {

    private val functions = arrayOf(
        // String
        "strlen", "substr", "strpos", "strrpos", "str_replace", "str_pad", "str_repeat",
        "strtolower", "strtoupper", "ucfirst", "lcfirst", "trim", "ltrim", "rtrim",
        "sprintf", "printf", "number_format", "chunk_split", "wordwrap",
        "preg_match", "preg_match_all", "preg_replace", "preg_split",
        "nl2br", "htmlspecialchars", "htmlspecialchars_decode", "strip_tags",
        "md5", "sha1", "base64_encode", "base64_decode", "urlencode", "urldecode",
        "addslashes", "stripslashes",
        // Array
        "count", "array_key_exists", "array_keys", "array_values", "array_merge",
        "array_push", "array_pop", "array_shift", "array_unshift", "array_splice",
        "array_slice", "array_search", "array_unique", "array_flip", "array_combine",
        "array_map", "array_filter", "array_reduce", "array_walk",
        "in_array", "sort", "rsort", "asort", "arsort", "ksort", "krsort", "usort",
        "implode", "explode", "join",
        // Type / variable
        "intval", "floatval", "strval", "boolval",
        "is_array", "is_string", "is_int", "is_integer", "is_long", "is_float",
        "is_double", "is_bool", "is_null", "is_numeric", "is_object", "is_callable",
        "gettype", "settype", "var_dump", "print_r", "var_export",
        // Math
        "abs", "ceil", "floor", "round", "max", "min", "pow", "sqrt", "log",
        "fmod", "rand", "mt_rand", "mt_getrandmax",
        // Date / time
        "time", "mktime", "strtotime", "date", "microtime",
        // OOP / reflection
        "get_class", "get_parent_class", "class_exists", "interface_exists",
        "method_exists", "property_exists", "is_a", "get_object_vars",
        "call_user_func", "call_user_func_array",
        // JSON / serialization
        "json_encode", "json_decode", "serialize", "unserialize",
        // File / path
        "file_exists", "file_get_contents", "file_put_contents", "dirname", "basename",
        "realpath", "glob",
        // Misc
        "defined", "define", "constant", "func_get_args", "func_num_args",
        "trigger_error", "error_reporting", "extension_loaded"
    )

    override fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet) {
        val psiElement = parameters.originalPosition ?: return
        PsiTreeUtil.getParentOfType(psiElement, ZephirMethodBody::class.java) ?: return

        for (fn in functions) {
            result.addElement(
                LookupElementBuilder.create(fn)
                    .withTypeText("PHP")
                    .withInsertHandler { ctx, _ ->
                        ctx.document.insertString(ctx.tailOffset, "()")
                        ctx.editor.caretModel.moveToOffset(ctx.tailOffset - 1)
                    }
            )
        }
    }
}
