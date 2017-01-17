package ru.ulstu_team.ulstuschedule

import org.junit.Test
import org.junit.Assert.*
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleApiAdapter

class ScheduleApiAdapterTest{
    val api = ScheduleApiAdapter()

    @Test
    fun getListGroups(){
        val groups = api.getGroups()
        assertEquals(groups.size, 225)
    }

    @Test
    fun getNameOfGroup(){
        val groups = api.getGroups()
        assertEquals(groups.first().name, "ЛМКбд-11")
    }

    @Test
    fun getListLessonsOfGroup(){
        val group = api
                .getGroups()
                .filter { group -> group.name == "ПИбд-41"}
                .first()

        val lessons = api.getLessons(group)
        assertEquals(lessons.size, 36)
    }

    @Test
    fun getLessonInfo(){
        val group = api
                .getGroups()
                .filter { group -> group.name == "ПИбд-41"}
                .first()

        val lessons = api.getLessons(group)
        val lesson = lessons
                .filter { lesson -> lesson.numberOfWeek == 1 && lesson.dayOfWeek == 2 && lesson.number == 4}
                .first()

        assertEquals("ИИС", lesson.name)
        assertEquals("КОРУНОВА Н В", lesson.teacher.name)
        assertEquals("а.411/3", lesson.cabinet)
    }
}