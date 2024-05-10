package com.zm.footballquiz.model.dto

import com.zm.footballquiz.statuspages.ApplicationException

enum class UpdateType {
    UPDATE, ADD, REMOVE;
}

data class UpdateBody(
    val value: Int,
    val type: String,
)

fun UpdateBody.toNewValue(
    oldValue: () -> Int,
): Int {
    val statisticsType = try {
        UpdateType.valueOf(type.uppercase())
    } catch (e: Exception) {
        throw ApplicationException.Generic("unknown type")
    }
    return when (statisticsType) {
        UpdateType.UPDATE -> value
        UpdateType.ADD -> oldValue.invoke() + value
        UpdateType.REMOVE -> oldValue.invoke() - value
    }
}