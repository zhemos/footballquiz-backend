package com.zm.footballquiz.db.dao.category

import com.zm.footballquiz.db.dao.BaseDao

object TableCategories : BaseDao("categories"), CategoryDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    override fun insertCategory() {
        TODO("Not yet implemented")
    }

    override fun getCategoryById() {
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
    fun insertCategory()
    fun getCategoryById()
    fun deleteCategoryById()
    fun getCategories()
    fun updateCategory()
}