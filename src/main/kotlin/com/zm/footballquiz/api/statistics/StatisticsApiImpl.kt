package com.zm.footballquiz.api.statistics

import com.zm.footballquiz.db.dao.SingleModeStatisticsDao
import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.UpdateSingleModeStatistics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object StatisticsApiImpl : StatisticsApi, KoinComponent {

    private val singleModeStatisticsDao by inject<SingleModeStatisticsDao>()

    override fun updateSingleModeStatistics(
        updateSingleModeStatistics: UpdateSingleModeStatistics,
    ): SingleModeStatistics? {
        return singleModeStatisticsDao.updateStatistics(updateSingleModeStatistics)
    }

    override fun getStatisticsById(id: Int): SingleModeStatistics? {
        return singleModeStatisticsDao.getStatisticsById(id)
    }
}