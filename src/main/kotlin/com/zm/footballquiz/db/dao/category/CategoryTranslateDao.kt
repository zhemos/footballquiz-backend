package com.zm.footballquiz.db.dao.category

import com.zm.footballquiz.db.dao.BaseDao
import com.zm.footballquiz.db.dao.TableLanguages

object TableCategoriesTranslate : BaseDao("categoriesTranslate"), CategoryTranslateDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val categoryId = integer("categoryId") references TableCategories.id
    val languageId = integer("languageId") references TableLanguages.id
    val name = varchar("name", 50)
    val description = varchar("description", 100).nullable()
}

interface CategoryTranslateDao