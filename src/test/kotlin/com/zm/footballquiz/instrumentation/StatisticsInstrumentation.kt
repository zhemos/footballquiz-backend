package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.UpdateSingleModeStatistics
import com.zm.footballquiz.model.dto.UpdateSingleModeStatisticsBody
import com.zm.footballquiz.model.dto.UpdateBody

object StatisticsInstrumentation {

    fun givenUpdateSingleModeStatistics(
        id: Int = 1,
        countOfPoints: Int = 0
    ) = UpdateSingleModeStatistics(
        id = id,
        countOfPoints = countOfPoints,
    )

    fun givenSingleModeStatistics(
        id: Int = 123,
        value: Int = 0
    ) = SingleModeStatistics(
        id = id,
        countOfPoints = value,
    )

    fun givenUpdateSingleModeStatisticsBodyOfTypeUpdate(
        value: Int = 10,
    ) = UpdateSingleModeStatisticsBody(
        countOfPointsUpdate = UpdateBody(value, "update"),
    )

    fun givenUpdateSingleModeStatisticsBodyOfTypeAdd(
        value: Int = 10,
    ) = UpdateSingleModeStatisticsBody(
        countOfPointsUpdate = UpdateBody(value, "add"),
    )

    fun givenUpdateSingleModeStatisticsBodyOfTypeRemove(
        value: Int = 10,
    ) = UpdateSingleModeStatisticsBody(
        countOfPointsUpdate = UpdateBody(value, "remove"),
    )

    fun givenUpdateSingleModeStatisticsBodyOfTypeUnknown(
        value: Int = 10,
    ) = UpdateSingleModeStatisticsBody(
        countOfPointsUpdate = UpdateBody(value, "unknown"),
    )
}