package com.zm.footballquiz.api.category

import com.zm.footballquiz.model.Category
import com.zm.footballquiz.model.dto.CreateCategoryBody
import com.zm.footballquiz.model.dto.UpdateCategoryBody

object CategoryApiImpl : CategoryApi {
    override fun insertCategory(createCategoryBody: CreateCategoryBody): Int? {
        TODO("Not yet implemented")
    }

    override fun updateCategory(updateCategoryBody: UpdateCategoryBody): Category? {
        TODO("Not yet implemented")
    }

    override fun deleteCategoryById(id: Int): Int? {
        TODO("Not yet implemented")
    }

    override fun getCategoryById(id: Int): Category? {
        TODO("Not yet implemented")
    }

    override fun getCategories(): List<Category> {
        TODO("Not yet implemented")
    }
}