package com.zm.footballquiz.dao

import com.zm.footballquiz.db.dao.TableLanguages
import com.zm.footballquiz.db.dao.category.TableCategories
import com.zm.footballquiz.db.dao.category.TableCategoriesTranslate
import com.zm.footballquiz.instrumentation.CategoryInstrumentation.givenCategoryTranslate
import com.zm.footballquiz.instrumentation.CategoryInstrumentation.givenCreateCategoryTranslateBody
import com.zm.footballquiz.instrumentation.LanguageInstrumentation
import com.zm.footballquiz.instrumentation.LanguageInstrumentation.givenLanguage
import com.zm.footballquiz.model.dto.CreateCategoryBody
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TableCategoriesTranslateTest : BaseDaoTest() {

    private val language = givenLanguage("en", "English")

    @Test
    fun `insert and get by id category translate`() = withTables(TableCategories, TableCategoriesTranslate, TableLanguages) {
        val languageId = TableLanguages.insertLanguage(language) ?: throw IllegalStateException("id cannot be null")
        val createCategoryTranslateBody = givenCreateCategoryTranslateBody("name", "desc", "en")
        val categoryId = TableCategories.insertCategory(CreateCategoryBody(listOf())) ?: throw IllegalStateException("id cannot be null")
        val id = TableCategoriesTranslate.insertCategoryTranslate(categoryId, createCategoryTranslateBody) ?: throw IllegalStateException("id cannot be null")
//        val categoryTranslate = givenCategoryTranslate()

//        val actual = TableCategoriesTranslate.getCategoryTranslateByCategoryId(id)
//        assertEquals(categoryTranslate, actual)
    }
}