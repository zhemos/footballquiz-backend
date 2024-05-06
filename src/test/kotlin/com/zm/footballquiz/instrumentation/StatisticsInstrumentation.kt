package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.dto.SingleModeStatisticsBody

object StatisticsInstrumentation {

    fun givenSingleModeStatisticsBody(countOfPoints: Int = 0) = SingleModeStatisticsBody(
        countOfPoints = countOfPoints,
    )

    fun givenSingleModeStatistics(id: Int = 123) = SingleModeStatistics(
        id = id,
        countOfPoints = 0,
    )
}