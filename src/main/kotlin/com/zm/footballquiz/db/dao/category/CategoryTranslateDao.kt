package com.zm.footballquiz.db.dao.category

import com.zm.footballquiz.db.dao.BaseDao
import com.zm.footballquiz.db.dao.TableLanguages
import com.zm.footballquiz.model.CategoryTranslate
import com.zm.footballquiz.model.dto.CreateCategoryTranslateBody
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object TableCategoriesTranslate : BaseDao("categoriesTranslate"), CategoryTranslateDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val categoryId = reference(
        name = "categoryId",
        refColumn = TableCategories.id,
        onDelete = ReferenceOption.RESTRICT,
        onUpdate = ReferenceOption.CASCADE,
    )
    val languageId = reference(
        name = "languageId",
        refColumn = TableLanguages.id,
        onDelete = ReferenceOption.RESTRICT,
        onUpdate = ReferenceOption.CASCADE,
    )
    val name = varchar("name", 50)
    val description = varchar("description", 100).nullable()

    override fun insertCategoryTranslate(
        categoryId: Int,
        createCategoryTranslateBody: CreateCategoryTranslateBody,
    ): Int? {
        val languageId = TableLanguages.getLanguageByCode(createCategoryTranslateBody.languageCode)?.id ?: return null
        return insert {
            it[name] = createCategoryTranslateBody.name
            it[description] = createCategoryTranslateBody.description
            it[TableCategoriesTranslate.categoryId] = categoryId
            it[TableCategoriesTranslate.languageId] = languageId
            it[dateCreated] = System.currentTimeMillis()
            it[dateUpdated] = System.currentTimeMillis()
        }.getOrNull(id)
    }

    override fun updateCategoryTranslate(categoryId: Int, categoryTranslate: CategoryTranslate) {
        val languageId = TableLanguages.getLanguageByCode(categoryTranslate.code)?.id ?: return
        update(
            {
                (TableCategoriesTranslate.categoryId eq categoryId) and
                (TableCategoriesTranslate.languageId eq languageId)
            },
        ) { statement ->
            statement[name] = categoryTranslate.name
            statement[description] = categoryTranslate.description
        }
    }

    override fun deleteCategoryTranslateByCategoryId(categoryId: Int): Int {
        return deleteWhere { TableCategoriesTranslate.categoryId eq categoryId }
    }

    override fun getCount(): Long = selectAll().count()
}

fun ResultRow.mapRowToCategoryTranslate() = CategoryTranslate(
    code = this[TableLanguages.code],
    name = this[TableCategoriesTranslate.name],
    description = this[TableCategoriesTranslate.description],
)

interface CategoryTranslateDao {
    fun insertCategoryTranslate(
        categoryId: Int,
        createCategoryTranslateBody: CreateCategoryTranslateBody
    ): Int?
    fun updateCategoryTranslate(
        categoryId: Int,
        categoryTranslate: CategoryTranslate,
    )
    fun deleteCategoryTranslateByCategoryId(categoryId: Int): Int
    fun getCount(): Long
}