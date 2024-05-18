package com.zm.footballquiz.model

data class CategoryTranslate(
    val code: String,
    val name: String,
    val description: String?,
)

data class SingleCategory(
    val id: Int,
)

data class Category(
    val id: Int,
    val translates: List<CategoryTranslate>,
)