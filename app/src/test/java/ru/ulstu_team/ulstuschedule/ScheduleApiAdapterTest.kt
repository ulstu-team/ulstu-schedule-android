package ru.ulstu_team.ulstuschedule

import org.junit.Test
import org.junit.Assert.*
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleApiAdapter

class ScheduleApiAdapterTest{
    @Test
    fun getGroups(){
        val api = ScheduleApiAdapter()
        assertEquals(api.getGroups().size, 225)
    }
}