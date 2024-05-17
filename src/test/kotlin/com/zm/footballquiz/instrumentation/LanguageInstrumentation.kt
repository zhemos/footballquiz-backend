package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.Language

object LanguageInstrumentation {

    fun givenLanguage(
        code: String,
        name: String,
    ) = Language(
        id = 1,
        code = code,
        name = name,
    )
}