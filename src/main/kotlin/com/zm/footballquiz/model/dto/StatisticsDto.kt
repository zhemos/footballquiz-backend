package com.zm.footballquiz.model.dto

enum class UpdateStatisticsType {
    UPDATE, ADD;
}

data class UpdateStatisticsBody(
    val value: Int,
    val type: String,
)

data class UpdateSingleModeStatisticsBody(
    val countOfPointsUpdate: UpdateStatisticsBody,
)

data class SingleModeStatisticsResponse(
    val countOfPoints: Int,
)