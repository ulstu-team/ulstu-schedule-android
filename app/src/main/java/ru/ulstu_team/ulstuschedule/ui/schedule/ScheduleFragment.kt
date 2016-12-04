package ru.ulstu_team.ulstuschedule.ui.schedule

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.model.ScheduleOfDay
import ru.ulstu_team.ulstuschedule.ui.main.DaySchedulesAdapter
import java.util.*

class ScheduleFragment : Fragment() {

    private val daySchedulesAdapter = DaySchedulesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return with(ctx) {
            verticalLayout {
                lparams {
                    width = matchParent
                    height = matchParent
                }
                recyclerView {
                    adapter = daySchedulesAdapter
                    layoutManager = LinearLayoutManager(ctx)
                    setHasFixedSize(true)
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setupFakeData()
    }

    private fun setupFakeData() {
        daySchedulesAdapter.setDaySchedules(listOf(
                ScheduleOfDay(listOf(Lesson().apply {
                    number = 1
                    name = "Программирование"
                    cabinet = "a.431/3"
                    teacher = ru.ulstu_team.ulstuschedule.data.model.Teacher().apply { name = "Эгов Е.Н." }
                }, Lesson().apply {
                    number = 2
                    name = "Ин.Яз."
                    cabinet = "a.401/г"
                    teacher = ru.ulstu_team.ulstuschedule.data.model.Teacher().apply { name = "Новосельцева Н.Н." }
                }, Lesson().apply {
                    number = 3
                    name = "Физика"
                    cabinet = "a.801/г"
                    teacher = ru.ulstu_team.ulstuschedule.data.model.Teacher().apply { name = "Качаев А.А." }
                }), Date()),
                ScheduleOfDay(listOf(
                        Lesson().apply {
                            number = 1
                            name = "Программирование"
                            cabinet = "a.431/3"
                            teacher = ru.ulstu_team.ulstuschedule.data.model.Teacher().apply { name = "Эгов Е.Н." }
                        }
                ), Date())))
    }

    companion object {
        val TAG = "ScheduleFragment"

        fun newInstance(): ScheduleFragment {
            val scheduleFragment = ScheduleFragment()
            scheduleFragment.arguments = Bundle()
            return scheduleFragment
        }
    }

}
