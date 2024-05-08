package com.zm.footballquiz.modules.statistics

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.UpdateSingleModeStatistics
import com.zm.footballquiz.model.dto.SingleModeStatisticsResponse
import com.zm.footballquiz.model.dto.UpdateSingleModeStatisticsBody
import com.zm.footballquiz.model.dto.UpdateStatisticsBody
import com.zm.footballquiz.model.dto.UpdateStatisticsType
import com.zm.footballquiz.statuspages.ApplicationException

fun UpdateStatisticsBody.toNewValue(
    oldValue: () -> Int,
): Int {
    val statisticsType = try {
        UpdateStatisticsType.valueOf(type.uppercase())
    } catch (e: Exception) {
        throw ApplicationException.Generic("unknown type")
    }
    return when (statisticsType) {
        UpdateStatisticsType.UPDATE -> value
        UpdateStatisticsType.ADD -> oldValue.invoke() + value
    }
}

fun SingleModeStatistics.toDto(): SingleModeStatisticsResponse {
    return SingleModeStatisticsResponse(countOfPoints = countOfPoints)
}