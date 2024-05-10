package com.zm.footballquiz.modules.statistics

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.dto.SingleModeStatisticsResponse

fun SingleModeStatistics.toDto(): SingleModeStatisticsResponse {
    return SingleModeStatisticsResponse(countOfPoints = countOfPoints)
}