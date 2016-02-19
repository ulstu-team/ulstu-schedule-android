package ru.ulstu_team.ulstuschedule.util

object TimeStringUtils {

    private val beginHours = arrayOf("8", "9", "11", "13", "14", "16", "18", "19")
    private val beginMinutes = arrayOf("00", "40", "30", "10", "50", "30", "10", "50")
    private val endHours = arrayOf("9", "11", "13", "14", "16", "18", "19", "21")
    private val endMinutes = arrayOf("30", "10", "00", "40", "20", "00", "40", "20")

    fun beginHour(lessonNumber: Int) = beginHours[lessonNumber - 1]

    fun beginMinutes(lessonNumber: Int) = beginMinutes[lessonNumber - 1]

    fun endHour(lessonNumber: Int) = endHours[lessonNumber - 1]

    fun endMinutes(lessonNumber: Int) = endMinutes[lessonNumber - 1]
}
