package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.UpdateSingleModeStatistics
import com.zm.footballquiz.model.dto.UpdateSingleModeStatisticsBody
import com.zm.footballquiz.model.dto.UpdateStatisticsBody
import com.zm.footballquiz.model.dto.UpdateStatisticsType

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
        countOfPointsUpdate = UpdateStatisticsBody(value, "update"),
    )

    fun givenUpdateSingleModeStatisticsBodyOfTypeAdd(
        value: Int = 10,
    ) = UpdateSingleModeStatisticsBody(
        countOfPointsUpdate = UpdateStatisticsBody(value, "add"),
    )

    fun givenUpdateSingleModeStatisticsBodyOfTypeUnknown(
        value: Int = 10,
    ) = UpdateSingleModeStatisticsBody(
        countOfPointsUpdate = UpdateStatisticsBody(value, "unknown"),
    )
}