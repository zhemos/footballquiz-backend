package com.zm.footballquiz.modules.category

import com.zm.footballquiz.model.dto.CategoryResponse
import com.zm.footballquiz.model.dto.CreateCategoryBody
import com.zm.footballquiz.model.dto.UpdateCategoryBody

interface CategoryController {

    fun insertCategory(createCategoryBody: CreateCategoryBody)

    fun updateCategory(updateCategoryBody: UpdateCategoryBody)

    fun deleteCategoryById(id: Int)

    fun getCategoryById(id: Int): CategoryResponse?

    fun getCategories(): List<CategoryResponse>
}