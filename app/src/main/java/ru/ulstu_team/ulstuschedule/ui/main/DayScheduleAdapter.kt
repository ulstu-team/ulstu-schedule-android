package ru.ulstu_team.ulstuschedule.ui.main

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.model.ScheduleOfDay
import ru.ulstu_team.ulstuschedule.data.remote.Schedule
import java.util.*
import java.util.Calendar.*

class DaySchedulesAdapter() : RecyclerView.Adapter<DaySchedulesAdapter.DayScheduleViewHolder>() {

    var mDaySchedules: List<ScheduleOfDay> = ArrayList()

    fun setDaySchedules(daySchedules: List<ScheduleOfDay>) {
        mDaySchedules = daySchedules
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DayScheduleViewHolder {
        return DayScheduleViewHolder(scheduleView(parent!!.context))
    }

    override fun onBindViewHolder(holder: DayScheduleViewHolder?, position: Int) {
        if (holder == null) return
        val context = holder.itemView.context

        val scheduleOfDay = mDaySchedules[position]

        val cal = Calendar.getInstance()
        cal.time = scheduleOfDay.date
        val dateText = "${weekDay(cal.get(DAY_OF_WEEK), context).capitalize()}, " +
                " ${cal.get(DAY_OF_MONTH)} ${month(cal.get(MONTH), context)}"
        holder.tvTitle.text = dateText

        val addLesson = { ctx: Context, lesson: Lesson ->
            val lessonInfoTextSize = 13f

            with(ctx) {
                relativeLayout {
                    id = R.id.lessonContainer
                    lparams {
                        width = matchParent
                        topMargin = dip(8)
                        bottomMargin = dip(8)
                    }

                    textView {
                        id = R.id.lessonTime
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        textSize = lessonInfoTextSize
                        gravity = Gravity.END
                        text = time(lesson.number)
                    }.lparams {
                        marginEnd = dip(16)
                        alignParentStart()
                        alignStart(R.id.lessonName)
                    }
                    textView {
                        id = R.id.cabinet
                        textSize = lessonInfoTextSize
                        textColor = R.color.secondaryText
                        text = lesson.cabinet
                    }.lparams {
                        alignParentEnd()
                        bottomMargin = dip(4)
                    }
                    textView {
                        id = R.id.lessonNumber
                        textSize = lessonInfoTextSize
                        textColor = R.color.secondaryText
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        text = lesson.number.toString()
                    }.lparams {
                        alignParentEnd()
                        below(R.id.cabinet)
                    }
                    textView {
                        id = R.id.teacher
                        setPadding(0, 0, dip(8), 0)
                        textSize = lessonInfoTextSize
                        textColor = R.color.secondaryText
                        text = lesson.teacher.name
                    }.lparams {
                        alignParentStart()
                        alignEnd(R.id.cabinet)
                        bottomMargin = dip(4)
                        marginStart = dip(64)
                    }
                    textView {
                        id = R.id.lessonName
                        textSize = lessonInfoTextSize
                        textColor = R.color.secondaryText
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        text = lesson.name
                    }.lparams {
                        alignStart(R.id.teacher)
                        below(R.id.teacher)
                        alignEnd(R.id.lessonNumber)
                    }
                }
            }
        }

        scheduleOfDay.lessons.forEach {
            holder.lessensContainer.removeAllViews()
            holder.lessensContainer.addView(addLesson(holder.itemView.context, it))
        }
    }

    override fun getItemCount() = mDaySchedules.count()

    private fun scheduleView(context: Context): View = with(context) {
        verticalLayout {
            lparams {
                bottomMargin = dip(8)
                leftMargin = dip(16)
                rightMargin = dip(16)
            }
            cardView {
                lparams { width = matchParent }
                cardElevation = 32f

                relativeLayout {
                    id = R.id.contentContainer
                    lparams {
                        width = matchParent
                        padding = dimen(R.dimen.schedule_view_padding)
                    }

                    imageView {
                        id = R.id.imgWeekColor
                        backgroundResource = R.drawable.blue_circle
                    }.lparams {
                        width = dip(10); height = dip(10); margin = dip(6)
                    }
                    textView {
                        id = R.id.tvDay
                        setPadding(dip(6), 0, 0, 0)
                        textSize = 15.toFloat()
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    }.lparams {
                        alignParentTop()
                        rightOf(R.id.imgWeekColor)
                        bottomMargin = dip(8)
                    }
                    verticalLayout {
                        id = R.id.lessonContainer
                        orientation = LinearLayout.VERTICAL
                    }.lparams { width = matchParent; below(R.id.tvDay) }

                    view {
                        id = R.id.divider
                        backgroundResource = R.color.schedule_view_divider
                    }.lparams {
                        width = dip(0.5.toFloat())
                        bottomOf(R.id.lessonContainer)
                        below(R.id.tvDay)
                        marginStart = dip(56)
                        topMargin = dip(8)
                    }
                }
            }
        }
    }

    inner class DayScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgWeekColor = itemView.find<ImageView>(R.id.imgWeekColor)
        val tvTitle = itemView.find<TextView>(R.id.tvDay)
        val lessensContainer = itemView.find<LinearLayout>(R.id.lessonContainer)
    }

    // TODO: Move to core layer
    fun weekDay(index: Int, context: Context): String {
        if (Companion.weekDays == null) {
            Companion.weekDays = context.resources.getStringArray(R.array.full_days_of_week)
        }
        return Companion.weekDays!![index - 1]
    }

    // TODO: Move to core layer
    fun month(index: Int, context: Context): String {
        if (Companion.months == null) {
            Companion.months = context.resources.getStringArray(R.array.months)
        }
        return Companion.months!![index - 1]
    }

    // TODO: Move to core layer
    fun time(lessonNumber: Int): String {
        assert(lessonNumber >= 1 && lessonNumber <= 8)
        return when (lessonNumber) {
            1 -> "8:00"
            2 -> "9:40"
            3 -> "11:30"
            4 -> "13:10"
            5 -> "14:40"
            6 -> "16:20"
            7 -> "18:00"
            8 -> "19:40"
            else -> ""
        }
    }

    // TODO: Move to core layer
    object Companion {
        var weekDays: Array<String>? = null
        var months: Array<String>? = null
    }
}