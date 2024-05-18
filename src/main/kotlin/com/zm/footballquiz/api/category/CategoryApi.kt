package com.zm.footballquiz.api.category

import com.zm.footballquiz.model.Category
import com.zm.footballquiz.model.dto.CreateCategoryBody
import com.zm.footballquiz.model.dto.UpdateCategoryBody

interface CategoryApi {

    fun insertCategory(createCategoryBody: CreateCategoryBody): Int?

    fun updateCategory(updateCategoryBody: UpdateCategoryBody): Category?

    fun deleteCategoryById(id: Int): Int?

    fun getCategoryById(id: Int): Category?

    fun getCategories(): List<Category>
}