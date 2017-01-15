package ru.ulstu_team.ulstuschedule.ui.schedule

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.ctx
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.model.ScheduleOfDay
import ru.ulstu_team.ulstuschedule.data.model.Teacher
import java.util.*

class ScheduleFragment : Fragment() {

    private val daySchedulesAdapter = DaySchedulesAdapter()
    private val ui = ScheduleFragmentUI()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        val view = ui.createView(AnkoContext.create(ctx, this))
        ui.rvSchedules.adapter = daySchedulesAdapter
        return view
    }

    override fun onStart() {
        super.onStart()
        setupFakeData()
        // setupCalendar()
    }

    private fun setupCalendar() {
        val days = arrayOf("П", "В", "С", "Ч", "П", "С")
        for (i in 0 until days.count())
            ui.calendarGrid.addView(TextView(activity).apply { text = days[i] })
    }

    private fun setupFakeData() {
        fun randomScheduleOfDay() : ScheduleOfDay {
            val teachers = arrayOf("Эгов Е.Н.", "Новосельцева Н.Н.", "Романов А.А.", "Воронина В.В.", "Афанасьева Т.В.", "Качаев А.А.")
            val subjects = arrayOf("Программирование", "Ин.Яз.", "Физика", "Алгоритмы и структуры данных", "Выш. матан", "РВИП", "ТПО")
            val cabinets = arrayOf("a.401/г", "a.431/3", "a.801/г")

            val random = Random()
            val lessons = ArrayList<Lesson>()

            for (i in 2..random.nextInt(5)) {
                lessons.add(Lesson().apply {
                    number = random.nextInt(8) + 1
                    numberOfWeek = 1 + random.nextInt(2)
                    dayOfWeek = random.nextInt(6)
                    name = subjects[random.nextInt(subjects.count())]
                    cabinet = cabinets[random.nextInt(cabinets.count())]
                    teacher = Teacher().apply { name = teachers[random.nextInt(teachers.count())] }
                })
            }

            return ScheduleOfDay(lessons, Date(Date().time - random.nextInt(432898970) * 100 - 432890689689))
        }

        val schedules = ArrayList<ScheduleOfDay>()
        for (i in 1..12)
            schedules.add(randomScheduleOfDay())

        daySchedulesAdapter.setDaySchedules(schedules)
    }



    companion object {
        val TAG = "ScheduleFragment"

        fun newInstance() = ScheduleFragment().apply { arguments = Bundle() }
    }

}
