package com.zm.footballquiz.dao

import com.zm.footballquiz.db.dao.TableLanguages
import com.zm.footballquiz.instrumentation.LanguageInstrumentation.givenLanguage
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TableLanguagesTest : BaseDaoTest() {

    @Test
    fun `insert and get by code language`() = withTables(TableLanguages) {
        val code = "uk"
        val language = givenLanguage(code, "English")
        TableLanguages.insertLanguage(language) ?: throw IllegalStateException("id cannot be null")

        val actual = TableLanguages.getLanguageByCode(code)
        assertEquals(language, actual)
    }

    @Test
    fun `check count languages`() = withTables(TableLanguages) {
        val language1 = givenLanguage("uk", "English")
        val language2 = givenLanguage("ru", "Russian")
        val language3 = givenLanguage("fr", "French")
        TableLanguages.insertLanguage(language1)
        TableLanguages.insertLanguage(language2)
        TableLanguages.insertLanguage(language3)

        assertEquals(3, TableLanguages.getCount())
    }
}