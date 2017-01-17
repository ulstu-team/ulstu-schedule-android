package ru.ulstu_team.ulstuschedule.ui.schedule

import android.app.Fragment
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.GridLayout
import android.widget.TextView
import org.jetbrains.anko.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.model.ScheduleOfDay
import ru.ulstu_team.ulstuschedule.data.model.Teacher
import ru.ulstu_team.ulstuschedule.util.DateFormatter
import ru.ulstu_team.ulstuschedule.util.context
import ru.ulstu_team.ulstuschedule.util.getColorResource
import java.util.*

class ScheduleFragment : Fragment(), ViewTreeObserver.OnGlobalLayoutListener {

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

        view.viewTreeObserver.addOnGlobalLayoutListener (this)
    }

    override fun onGlobalLayout() {
        setupCalendar()
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    private fun setupCalendar() {
        val cellWidth = ui.calendarContainer.width / 7
        val cellHeight = dip(20)

        val days = arrayOf("П", "В", "С", "Ч", "П", "С", "В")
        for (i in 0 until days.count())
            ui.calendarGrid.addView(
                    TextView(activity).apply {
                        text = days[i]
                        textColor = context().getColorResource(R.color.white)
                        typeface = Typeface.DEFAULT_BOLD
                        gravity = Gravity.CENTER
                        layoutParams = GridLayout.LayoutParams().apply {
                            width = cellWidth; height = cellHeight
                        }
                    })

        val dateFormatter = DateFormatter(Date(), context())

        ui.tvCurrentDay.text = dateFormatter.getDay().toString()
        ui.tvCurrentMonth.text = dateFormatter.getMonthString()

        val monthWeeks = dateFormatter.getWeeks()
        for (i in 0 until monthWeeks.count()) {
            for (j in 0 until monthWeeks[i].count()) {
                ui.calendarGrid.addView(
                        TextView(activity).apply {
                            text = if (monthWeeks[i][j] != 0) { monthWeeks[i][j].toString() } else { "" }
                            textColor = context().getColorResource(R.color.colorCalendarText)
                            gravity = Gravity.CENTER
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = cellWidth; height = cellHeight
                            }
                        })
            }
        }
    }

    private fun setupFakeData() {
        fun randomScheduleOfDay() : ScheduleOfDay {
            val teachers = arrayOf("Эгов Е.Н.", "Новосельцева Н.Н.", "Романов А.А.", "Воронина В.В.", "Афанасьева Т.В.", "Качаев А.А.")
            val subjects = arrayOf("Программирование", "Ин.Яз.", "Физика", "Алгоритмы и структуры данных", "Выш. матан", "РВИП", "ТПО")
            val cabinets = arrayOf("a.401/г", "a.431/3", "a.801/г")

            val random = Random()
            val lessons = ArrayList<Lesson>()

            for (i in 1..(random.nextInt(5) + 1)) {
                lessons.add(Lesson().apply {
                    number = random.nextInt(8) + 1
                    numberOfWeek = 1 + random.nextInt(2)
                    dayOfWeek = random.nextInt(6) + 1
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
