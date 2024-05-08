package com.zm.footballquiz.api.statistics

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.UpdateSingleModeStatistics

interface StatisticsApi {

    fun updateSingleModeStatistics(
        updateSingleModeStatistics: UpdateSingleModeStatistics,
    ): SingleModeStatistics?

    fun getStatisticsById(id: Int): SingleModeStatistics?
}