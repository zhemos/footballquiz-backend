package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.Category
import com.zm.footballquiz.model.CategoryTranslate
import com.zm.footballquiz.model.dto.CreateCategoryBody
import com.zm.footballquiz.model.dto.CreateCategoryTranslateBody

object CategoryInstrumentation {

    fun givenCreateCategoryTranslateBody(
        name: String,
        description: String,
        languageCode: String,
    ) = CreateCategoryTranslateBody(
        name = name,
        description = description,
        languageCode = languageCode,
    )

    fun givenCreateCategoryBody(
        translate: List<CreateCategoryTranslateBody>
    ) = CreateCategoryBody(
        translate = translate,
    )

    fun givenCategoryTranslate(
        code: String,
        name: String,
        description: String,
    ) = CategoryTranslate(
        code = code,
        name = name,
        description = description,
    )

    fun givenCategory(
        id: Int,
        translates: List<CategoryTranslate>,
    ) = Category(
        id = id,
        translates = translates,
    )
}