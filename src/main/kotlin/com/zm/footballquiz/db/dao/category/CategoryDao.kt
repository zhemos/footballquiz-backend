package com.zm.footballquiz.db.dao.category

import com.zm.footballquiz.db.dao.BaseDao
import com.zm.footballquiz.model.Category

object TableCategories : BaseDao("categories"), CategoryDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    override fun insertCategory(): Int? {
        TODO("Not yet implemented")
    }

    override fun getCategoryById(id: Int): Category? {
        TODO("Not yet implemented")
    }

    override fun deleteCategoryById() {
        TODO("Not yet implemented")
    }

    override fun getCategories() {
        TODO("Not yet implemented")
    }

    override fun updateCategory() {
        TODO("Not yet implemented")
    }
}

interface CategoryDao {
    fun insertCategory(): Int?
    fun getCategoryById(id: Int): Category?
    fun deleteCategoryById()
    fun getCategories()
    fun updateCategory()
}