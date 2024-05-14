package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.Language

object LanguageInstrumentation {

    fun givenLanguage(
        code: String,
        name: String,
    ) = Language(
        code = code,
        name = name,
    )
}