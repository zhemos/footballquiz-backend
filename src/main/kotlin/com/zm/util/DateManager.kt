package com.zm.util

import com.zm.util.DateManager.DEFAULT_PATTERN
import java.text.SimpleDateFormat
import java.util.Calendar

object DateManager : DateManagerContract {
    internal const val DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"

    override fun convertStringToCalendar(str: String, pattern: String): Calendar? {
        val dateFormat = SimpleDateFormat(pattern)
        return try {
            Calendar.getInstance().also { calendar ->
                dateFormat.parse(str)?.let {
                    calendar.time = it
                }
                //calendar.add(Calendar.MILLISECOND, Calendar.getInstance().timeZone.rawOffset)
            }
        } catch (e: Exception) { null }
    }

    override fun convertCalendarToString(calendar: Calendar, pattern: String): String? {
        return try {
            val format = SimpleDateFormat(pattern)
            format.format(calendar.time)
        } catch (e: Exception) { null }
    }
}

interface DateManagerContract {
    fun convertStringToCalendar(str: String, pattern: String = DEFAULT_PATTERN): Calendar?
    fun convertCalendarToString(calendar: Calendar, pattern: String = DEFAULT_PATTERN): String?
}