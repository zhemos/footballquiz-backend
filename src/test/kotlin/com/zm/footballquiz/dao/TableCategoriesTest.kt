package com.zm.footballquiz.dao

import com.zm.footballquiz.db.dao.category.TableCategories
import org.junit.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TableCategoriesTest : BaseDaoTest() {

    @Test
    fun `insert and get by id category`() {
        TableCategories.insertCategory()
    }
}