package com.zm.footballquiz.model.dto

data class CreateCategoryTranslateBody(
    val name: String,
    val description: String,
    val languageCode: String,
)

data class CreateCategoryBody(
    val translate: List<CreateCategoryTranslateBody>,
)