package com.zm.footballquiz.modules.statistics

import com.zm.footballquiz.model.dto.SingleModeStatisticsResponse
import com.zm.footballquiz.model.dto.UpdateSingleModeStatisticsBody

interface StatisticsController {

    suspend fun updateSingleModeStatistics(
        id: Int,
        updateSingleModeStatisticsBody: UpdateSingleModeStatisticsBody,
    ): SingleModeStatisticsResponse?
}