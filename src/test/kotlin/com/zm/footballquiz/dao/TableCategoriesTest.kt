package com.zm.footballquiz.dao

import com.zm.footballquiz.db.dao.TableLanguages
import com.zm.footballquiz.db.dao.category.TableCategories
import com.zm.footballquiz.db.dao.category.TableCategoriesTranslate
import com.zm.footballquiz.instrumentation.CategoryInstrumentation
import com.zm.footballquiz.instrumentation.CategoryInstrumentation.givenCategory
import com.zm.footballquiz.instrumentation.CategoryInstrumentation.givenCategoryTranslate
import com.zm.footballquiz.instrumentation.CategoryInstrumentation.givenCreateCategoryBody
import com.zm.footballquiz.instrumentation.CategoryInstrumentation.givenCreateCategoryTranslateBody
import com.zm.footballquiz.instrumentation.LanguageInstrumentation.givenLanguage
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TableCategoriesTest : BaseDaoTest() {

    private val languages = listOf(
        givenLanguage("en", "English"),
        givenLanguage("ru", "Russian"),
        givenLanguage("fr", "French")
    )

    private fun createLanguageTable() {
        languages.forEach {
            TableLanguages.insertLanguage(it)
        }
    }

    @Test
    fun `check language table`() = withTables(TableLanguages) {
        createLanguageTable()
        val actual = TableLanguages.getCount()
        assertEquals(3, actual)
    }

    @Test
    fun `insert and get by id category`() = withTables(TableCategories, TableCategoriesTranslate, TableLanguages) {
        createLanguageTable()
        val translate = languages.map {
            givenCreateCategoryTranslateBody(it.name, it.name, it.code)
        }
        val createCategoryBody = givenCreateCategoryBody(translate)
        val categoryId = TableCategories.insertCategory(createCategoryBody) ?: throw IllegalStateException("id cannot be null")
        val translates = languages.map {
            givenCategoryTranslate(it.code, it.name, it.name)
        }
        val category = givenCategory(categoryId, translates)
        val actual = TableCategories.getCategoryById(categoryId)

        assertEquals(category, actual)
        assertEquals(3, TableCategoriesTranslate.getCount())
        println(actual)
    }

    @Test
    fun `get all categories`() = withTables(TableCategories, TableCategoriesTranslate, TableLanguages) {
        createLanguageTable()
        val translate = languages.map {
            givenCreateCategoryTranslateBody(it.name, it.name, it.code)
        }
        val createCategoryBody = givenCreateCategoryBody(translate)
        TableCategories.insertCategory(createCategoryBody) ?: throw IllegalStateException("id cannot be null")

        val categories = TableCategories.getCategories()
        assertEquals(1, categories.size)
    }

    @Test
    fun `delete category by id`() = withTables(TableCategories, TableCategoriesTranslate, TableLanguages) {
        createLanguageTable()
        val translate = languages.map {
            givenCreateCategoryTranslateBody(it.name, it.name, it.code)
        }
        val createCategoryBody = givenCreateCategoryBody(translate)
        val categoryId = TableCategories.insertCategory(createCategoryBody) ?: throw IllegalStateException("id cannot be null")

        val actual = TableCategories.deleteCategoryById(categoryId)
        val categories = TableCategories.getCategories()

        assertEquals(categoryId, actual)
        assertEquals(0, categories.size)
        assertEquals(0, TableCategoriesTranslate.getCount())
        assertEquals(3, TableLanguages.getCount())
    }

    @Test
    fun `update category`() = withTables(TableCategories, TableCategoriesTranslate, TableLanguages) {
        createLanguageTable()
        val newName = "Another name"
        val newDesc = "Another desc"
        val translate = languages.map {
            givenCreateCategoryTranslateBody(it.name, it.name, it.code)
        }
        val createCategoryBody = givenCreateCategoryBody(translate)
        val categoryId = TableCategories.insertCategory(createCategoryBody) ?: throw IllegalStateException("id cannot be null")

        val categories = TableCategories.getCategoryById(categoryId) ?: throw IllegalStateException("category cannot be null")
        val translates = categories.translates.map {
            if (it.code == languages.first().code) {
                givenCategoryTranslate(it.code, newName, newDesc)
            } else it
        }
        val newTranslate = givenCategoryTranslate(languages.first().code, newName, newDesc)
        val newCategory = categories.copy(translates = listOf(newTranslate))
        val actual = TableCategories.updateCategory(newCategory)?.translates

        assertEquals(translates, actual)
    }
}