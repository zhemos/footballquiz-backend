package com.zm.footballquiz.dao

import com.zm.footballquiz.db.dao.TableSingleModeStatistics
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenSingleModeStatistics
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenUpdateSingleModeStatistics
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TableSingleModeStatisticsTest : BaseDaoTest() {

    @Test
    fun `insert and get by id statistics`() = withTables(TableSingleModeStatistics) {
        val statistics = givenSingleModeStatistics(id = 1)
        val id = TableSingleModeStatistics.insertStatistics()
        id?.let {
            val singleModeStatistics = TableSingleModeStatistics.getStatisticsById(it)
            assertEquals(statistics, singleModeStatistics)
        } ?: throw IllegalStateException("id cannot be null")
    }

    @Test
    fun `update statistics`() = withTables(TableSingleModeStatistics) {
        val countOfPoints = 100
        val id = TableSingleModeStatistics.insertStatistics() ?: throw IllegalStateException("id cannot be null")
        val updateStatistics = givenUpdateSingleModeStatistics(id = id, countOfPoints = countOfPoints)
        val statistics = givenSingleModeStatistics(id = id).copy(countOfPoints = countOfPoints)
        val actual = TableSingleModeStatistics.updateStatistics(updateStatistics)
        assertEquals(statistics, actual)
    }

    @Test
    fun `delete statistics`() = withTables(TableSingleModeStatistics) {
        val id = TableSingleModeStatistics.insertStatistics() ?: throw IllegalStateException("id cannot be null")
        val deleteId = TableSingleModeStatistics.deleteStatisticsById(id)
        assertEquals(id, deleteId)
    }
}