package com.zm.footballquiz.util

import org.junit.Assert.*
import org.junit.Test

class DateManagerTest {
    private val dateManager: DateManagerContract = DateManager

    @Test
    fun test() {
        val date = dateManager.convertStringToCalendar("2000-12-12 00:00:00")
        println("!!!")
        println(date)
    }
}