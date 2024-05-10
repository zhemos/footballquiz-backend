package com.zm.footballquiz.model.dto

data class UpdateSingleModeStatisticsBody(
    val countOfPointsUpdate: UpdateBody,
)

data class SingleModeStatisticsResponse(
    val countOfPoints: Int,
)