package ru.ulstu_team.ulstuschedule.util

import android.content.Context
import ru.ulstu_team.ulstuschedule.R
import java.util.*
import java.util.Calendar.*

open class DateFormatter(date: Date, context: Context) {

    private val calendar = Calendar.getInstance().apply {
        time = date
        firstDayOfWeek = MONDAY
    }
    private val months = context.resources.getStringArray(R.array.months)

    fun getMonthString() : String = months[calendar.get(MONTH)]
    fun getDay() = calendar.get(DAY_OF_MONTH)

    fun getWeeks() : Array<IntArray> {

        val countOfDaysInMonth = calendar.getActualMaximum(DAY_OF_MONTH)
        val countOfWeeksInMonth = calendar.getActualMaximum(WEEK_OF_MONTH)

        val monthWeeksArray = Array(countOfWeeksInMonth, { IntArray(7, {0}) })

        for (dayOfMonth in 1..countOfDaysInMonth) {
            calendar.set(DAY_OF_MONTH, dayOfMonth)

            val dayOfWeek = when (calendar.get(DAY_OF_WEEK)) {
                MONDAY -> 0
                TUESDAY -> 1
                WEDNESDAY -> 2
                THURSDAY -> 3
                FRIDAY -> 4
                SATURDAY -> 5
                SUNDAY -> 6
                else -> -1
            }
            val numberOfWeek = calendar.get(WEEK_OF_MONTH) - 1

            monthWeeksArray[numberOfWeek][dayOfWeek] = dayOfMonth
        }

        return monthWeeksArray
    }

}