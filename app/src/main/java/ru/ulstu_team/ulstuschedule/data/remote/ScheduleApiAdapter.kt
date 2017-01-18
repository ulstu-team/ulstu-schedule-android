package ru.ulstu_team.ulstuschedule.data.remote

import com.google.gson.*
import ru.ulstu_team.ulstuschedule.data.model.Group
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.model.ScheduleOfDay
import ru.ulstu_team.ulstuschedule.data.model.Teacher
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

class ScheduleApiAdapter() {
    private val daysOfWeek = listOf("mon", "tue", "wed", "thu", "fri", "sat")

    fun getGroups() : List<Group> {
        val request = "http://ec2-52-32-177-83.us-west-2.compute.amazonaws.com:3000/api/v1/groups"
        val groups = toJsonArray(URL(request).readText())
                .map { groupName ->
                    val group = Group()
                    group.name = groupName.asString
                    group
                }

        return groups
    }

    fun getLessons(group : Group) : List<Lesson> {
        val request =
                "http://ec2-52-32-177-83.us-west-2.compute.amazonaws.com:3000/api/v1/schedule?group=${group.name}"

        val json = toJsonObject(URL(request).readText())

        return toListLessons(json)
    }

    private fun toJsonArray(input: String) : JsonArray {
        return JsonParser().parse(input).asJsonArray
    }

    private fun toJsonObject(input: String) : JsonObject {
        return JsonParser().parse(input).asJsonObject
    }

    private fun toListLessons(json: JsonObject) : List<Lesson> {
        val numbersOfWeek = listOf(1, 2)
        return numbersOfWeek
                .map { week -> jsonWeekToListLessons(json[week.toString()].asJsonObject, week) }
                .flatten()
    }

    private fun jsonWeekToListLessons(json: JsonObject, week: Int) : List<Lesson> {
        return daysOfWeek
                .map { day -> jsonDayToListLessons(json[day].asJsonObject, week, day) }
                .flatten()
    }

    private fun jsonDayToListLessons(json: JsonObject, week: Int, day: String) : List<Lesson> {
        val numbersOfLesson = listOf(1, 2, 3, 4, 5, 6, 7)

        return numbersOfLesson
                .filter { number -> isNotEmptyLesson(json[number.toString()]
                        .asJsonObject) }
                .map { number -> jsonLessonToObjectLesson(json[number.toString()]
                        .asJsonObject, week, day, number) }
    }

    private fun isNotEmptyLesson(lesson : JsonObject) : Boolean {
        return lesson["subject"].asJsonArray[0].asString != "_"
    }

    private fun jsonLessonToObjectLesson(json: JsonObject, week: Int, day: String, number: Int) : Lesson {
        val lesson = Lesson()
        lesson.name = json["subject"].asJsonArray.joinToString("/").drop(1).dropLast(1)
        val teacher = Teacher()
        teacher.name = json["teachers"].asJsonArray.joinToString("/").drop(1).dropLast(1)
        lesson.teacher = teacher
        lesson.cabinet = json["classroom"].asJsonArray.joinToString("/").drop(1).dropLast(1)
        lesson.number = number
        lesson.numberOfWeek = week
        lesson.dayOfWeek = daysOfWeek.indexOf(day)

        return lesson
    }

    fun getSchedule(lessons : List<Lesson>) : List<ScheduleOfDay>{
        return lessons
                .filter { lesson -> lesson.numberOfWeek == 1}
                .groupBy { lesson -> lesson.dayOfWeek }
                .map { group -> ScheduleOfDay(group.value, getData(group.key)) }
    }

    private fun getData(dayOfWeek : Int) : Date {
        val c = Calendar.getInstance()
        c.time = Date()
        val nowDayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 2

        val delta = nowDayOfWeek - dayOfWeek
        return Date(Date().time - delta * TimeUnit.DAYS.toMillis(1))
    }
}