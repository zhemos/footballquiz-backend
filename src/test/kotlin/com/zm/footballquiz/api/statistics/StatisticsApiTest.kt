package com.zm.footballquiz.api.statistics

import com.zm.footballquiz.api.BaseApiTest
import com.zm.footballquiz.db.dao.SingleModeStatisticsDao
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenSingleModeStatistics
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenUpdateSingleModeStatistics
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatisticsApiTest : BaseApiTest() {

    private val singleModeStatisticsDao: SingleModeStatisticsDao = mockk()
    private val api: StatisticsApi = StatisticsApiImpl

    init {
        startInjection(
            module {
                single { singleModeStatisticsDao }
            }
        )
    }

    @Before
    fun before() {
        clearMocks(singleModeStatisticsDao)
    }

    @Test
    fun `when updating single mode statistics and it is success, we return statistics`() {
        val id = 123
        val statistics = givenSingleModeStatistics(id)
        every { singleModeStatisticsDao.updateStatistics(any()) } returns statistics

        val updateSingleModeStatistics = givenUpdateSingleModeStatistics(id)
        val actual = api.updateSingleModeStatistics(updateSingleModeStatistics)
        assertEquals(statistics, actual)
    }

    @Test
    fun `when updating single mode statistics and it is fail, we return fail`() {
        val updateSingleModeStatistics = givenUpdateSingleModeStatistics()
        every { singleModeStatisticsDao.updateStatistics(any()) } returns null

        val actual = api.updateSingleModeStatistics(updateSingleModeStatistics)
        assertEquals(null, actual)
    }

    @Test
    fun `when getting single mode statistics by id and it is success, we return statistics`() {
        val id = 123
        val statistics = givenSingleModeStatistics(id)
        every { singleModeStatisticsDao.getStatisticsById(any()) } returns statistics

        val actual = api.getStatisticsById(id)
        assertEquals(statistics, actual)
    }

    @Test
    fun `when getting single mode statistics by id and it is fail, we return fail`() {
        val id = 123
        every { singleModeStatisticsDao.getStatisticsById(any()) } returns null

        val actual = api.getStatisticsById(id)
        assertEquals(null, actual)
    }
}