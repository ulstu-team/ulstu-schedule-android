package ru.ulstu_team.ulstuschedule.data.remote

import ru.ulstu_team.ulstuschedule.data.model.Group
import java.net.URL

class ScheduleApiAdapter {
    fun getGroups() : List<Group> {
        val request = "http://ec2-52-32-177-83.us-west-2.compute.amazonaws.com:3000/api/v1/groups"
        val groups = URL(request)
                .readText()
                .split(',')
                .map { groupName ->
                    val group = Group()
                    group.name = groupName
                    group
                }

        return groups
    }
}