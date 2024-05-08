package com.zm.footballquiz.controllers.statistics

import com.zm.footballquiz.api.statistics.StatisticsApi
import com.zm.footballquiz.controllers.BaseControllerTest
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenSingleModeStatistics
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenUpdateSingleModeStatisticsBodyOfTypeAdd
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenUpdateSingleModeStatisticsBodyOfTypeUnknown
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenUpdateSingleModeStatisticsBodyOfTypeUpdate
import com.zm.footballquiz.model.dto.SingleModeStatisticsResponse
import com.zm.footballquiz.model.dto.UpdateStatisticsType
import com.zm.footballquiz.modules.statistics.StatisticsController
import com.zm.footballquiz.modules.statistics.StatisticsControllerImpl
import com.zm.footballquiz.statuspages.ApplicationException
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatisticsControllerTest : BaseControllerTest() {

    private val statisticsApi: StatisticsApi = mockk()
    private val controller: StatisticsController by lazy { StatisticsControllerImpl() }

    init {
        startInjection(
            module {
                single { statisticsApi }
            }
        )
    }

    @Before
    override fun before() {
        super.before()
        clearMocks(statisticsApi)
    }

    @Test
    fun `update statics type value of`() {
        val actualUpdate = UpdateStatisticsType.valueOf("UPDATE")
        val actualAdd = UpdateStatisticsType.valueOf("ADD")
        assertEquals(UpdateStatisticsType.UPDATE, actualUpdate)
        assertEquals(UpdateStatisticsType.ADD, actualAdd)
    }

    @Test
    fun `when update single mode statistics and type is unknown, return exception`() {
        val updateStatistics = givenUpdateSingleModeStatisticsBodyOfTypeUnknown()
        Assert.assertThrows(ApplicationException.Generic::class.java) {
            runBlocking { controller.updateSingleModeStatistics(1, updateStatistics) }
        }
    }

    @Test
    fun `when update single mode statistics and type is update, return new statistics`() {
        val value = 10
        val updateStatistics = givenUpdateSingleModeStatisticsBodyOfTypeUpdate(value = value)
        val statistics = givenSingleModeStatistics(value = value)
        coEvery { statisticsApi.updateSingleModeStatistics(any()) } returns statistics

        val expected = SingleModeStatisticsResponse(value)
        runBlocking {
            val actual = controller.updateSingleModeStatistics(1, updateStatistics)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `when update single mode statistics and type is add, return new statistics`() {
        val value = 10
        val updateStatistics = givenUpdateSingleModeStatisticsBodyOfTypeAdd(value = value)
        val statistics = givenSingleModeStatistics(value = value)
        coEvery { statisticsApi.getStatisticsById(any()) } returns statistics
        coEvery { statisticsApi.updateSingleModeStatistics(any()) } returns statistics

        val expected = SingleModeStatisticsResponse(value)
        runBlocking {
            val actual = controller.updateSingleModeStatistics(1, updateStatistics)
            assertEquals(expected, actual)
        }
    }
}