package ru.ulstu_team.ulstuschedule.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.model.ScheduleOfDay
import ru.ulstu_team.ulstuschedule.data.model.Teacher
import ru.ulstu_team.ulstuschedule.util.showToast
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        configureBottomNavigationBar()

        val adapter = DaySchedulesAdapter()
        adapter.setDaySchedules(listOf(
                ScheduleOfDay(listOf(Lesson().apply {
                    number = 1
                    name = "Программирование"
                    cabinet = "a.431/3"
                    teacher = Teacher().apply { name = "Эгов Е.Н." }
                }, Lesson().apply {
                    number = 2
                    name = "Ин.Яз."
                    cabinet = "a.401/г"
                    teacher = Teacher().apply { name = "Новосельцева Н.Н." }
                }, Lesson().apply {
                    number = 3
                    name = "Физика"
                    cabinet = "a.801/г"
                    teacher = Teacher().apply { name = "Качаев А.А." }
                }), Date()),
                ScheduleOfDay(listOf(
                        Lesson().apply {
                            number = 1
                            name = "Программирование"
                            cabinet = "a.431/3"
                            teacher = Teacher().apply { name = "Эгов Е.Н." }
                        }
                ), Date())))
        rvDaySchedules.adapter = adapter
        rvDaySchedules.layoutManager = LinearLayoutManager(this)
        rvDaySchedules.setHasFixedSize(true)
    }

    private fun configureBottomNavigationBar() {
        bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_my_schedule ->
                    showToast(R.string.bottom_bar_my_schedule)
                R.id.action_favorites ->
                    showToast(R.string.bottom_bar_favorite)
                R.id.action_settings ->
                    showToast(R.string.bottom_bar_settings)
            }
            false
        }
    }
}
