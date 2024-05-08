package com.zm.footballquiz.modules.statistics

import com.zm.footballquiz.api.statistics.StatisticsApi
import com.zm.footballquiz.model.UpdateSingleModeStatistics
import com.zm.footballquiz.model.dto.SingleModeStatisticsResponse
import com.zm.footballquiz.model.dto.UpdateSingleModeStatisticsBody
import com.zm.footballquiz.modules.BaseController
import com.zm.footballquiz.statuspages.ApplicationException
import org.koin.core.component.inject

class StatisticsControllerImpl : BaseController(), StatisticsController {

    private val statisticsApi by inject<StatisticsApi>()

    override suspend fun updateSingleModeStatistics(
        id: Int,
        updateSingleModeStatisticsBody: UpdateSingleModeStatisticsBody,
    ): SingleModeStatisticsResponse? = dbQuery {
        val newValue = updateSingleModeStatisticsBody.countOfPointsUpdate.toNewValue {
            return@toNewValue statisticsApi.getStatisticsById(id)?.countOfPoints ?: throw ApplicationException.DataNotFound
        }
        val updateSingleModeStatistics = UpdateSingleModeStatistics(id, newValue)
        return@dbQuery statisticsApi.updateSingleModeStatistics(updateSingleModeStatistics)?.toDto()
    }
}