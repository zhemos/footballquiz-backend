package com.zm.footballquiz.db.dao.category

import com.zm.footballquiz.db.dao.BaseDao
import com.zm.footballquiz.db.dao.TableLanguages
import com.zm.footballquiz.model.Category
import com.zm.footballquiz.model.CategoryTranslate
import com.zm.footballquiz.model.SingleCategory
import com.zm.footballquiz.model.dto.CreateCategoryBody
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object TableCategories : BaseDao("categories"), CategoryDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    override fun insertCategory(createCategoryBody: CreateCategoryBody): Int? {
        val categoryId = insert {
            it[dateCreated] = System.currentTimeMillis()
            it[dateUpdated] = System.currentTimeMillis()
        }.getOrNull(id) ?: return null
        createCategoryBody.translate.forEach {
            TableCategoriesTranslate.insertCategoryTranslate(categoryId, it)
        }
        return categoryId
    }

    override fun updateCategory(category: Category): Category? {
        category.translates.forEach {
            TableCategoriesTranslate.updateCategoryTranslate(category.id, it)
        }
        //later
        //update({ id eq category.id }) { statement -> }
        return getCategoryById(category.id)
    }

    override fun deleteCategoryById(id: Int): Int {
        TableCategoriesTranslate.deleteCategoryTranslateByCategoryId(id)
        return deleteWhere { TableCategories.id eq id }
    }

    override fun getCategoryById(id: Int): Category? {
        return (TableCategories innerJoin TableCategoriesTranslate innerJoin TableLanguages).select {
            TableCategories.id eq id
        }.mapQueryToCategory().singleOrNull()
    }

    override fun getCategories(): List<Category> {
        return (TableCategories innerJoin TableCategoriesTranslate innerJoin TableLanguages)
            .selectAll()
            .mapQueryToCategory()
    }
}

fun ResultRow.mapRowToSingleCategory() = SingleCategory(
    id = this[TableCategories.id],
)

fun Query.mapQueryToCategory(): List<Category> {
    val mapCategories = mutableMapOf<SingleCategory, List<CategoryTranslate>>()
    this.forEach { row ->
        val category = row.mapRowToSingleCategory()
        val translates = mutableListOf<CategoryTranslate>()
        mapCategories[category]?.let { translates.addAll(it) }
        translates.add(row.mapRowToCategoryTranslate())
        mapCategories[category] = translates
    }
    return mapCategories.map {
        Category(
            id = it.key.id,
            translates = it.value
        )
    }
}

interface CategoryDao {
    fun insertCategory(createCategoryBody: CreateCategoryBody): Int?
    fun updateCategory(category: Category): Category?
    fun deleteCategoryById(id: Int): Int
    fun getCategoryById(id: Int): Category?
    fun getCategories(): List<Category>
}