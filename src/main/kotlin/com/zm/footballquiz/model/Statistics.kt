package com.zm.footballquiz.model

data class SingleModeStatistics(
    val id: Int,
    val countOfPoints: Int,
)

data class UpdateSingleModeStatistics(
    val id: Int,
    val countOfPoints: Int,
)