package ru.ulstu_team.ulstuschedule.ui.old.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.model.LessonComparator
import ru.ulstu_team.ulstuschedule.util.FontUtil
import ru.ulstu_team.ulstuschedule.util.TimeStringUtils
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter
import java.util.*
import javax.inject.Inject

class StickyListScheduleAdapter
@Inject
constructor(private val mContext: Context) : BaseAdapter(), StickyListHeadersAdapter {

    private var mLessons: List<Lesson>? = null
    private var mIsTeacher: Boolean = false
    private val mInflater: LayoutInflater
    private var mDaysOfWeek: Array<String>? = null
    private var mMonths: Array<String>? = null
    private val calendar: Calendar

    init {
        mInflater = LayoutInflater.from(mContext)
        mLessons = emptyList<Lesson>()

        calendar = Calendar.getInstance()
        calendar.time = Date()
    }

    fun setLessons(lessons: List<Lesson>, isTeacher: Boolean) {
        mIsTeacher = isTeacher
        val l = ArrayList(lessons)
        Collections.sort(l, LessonComparator())
        mLessons = l
        notifyDataSetChanged()
    }

    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup): View {
        var headerView: View
        val holder: HeaderViewHolder
        if (convertView == null) {
            headerView = mInflater.inflate(R.layout.sticky_list_header, parent, false)
            holder = HeaderViewHolder(mContext, headerView)
            headerView.tag = holder
        } else {
            headerView = convertView
            holder = headerView.tag as HeaderViewHolder
        }
        val lesson = mLessons!![position]
        calendar.add(Calendar.DAY_OF_MONTH, getDayIncrement(lesson))

        val date = daysOfWeek[lesson.dayOfWeek - 1] + ", " +
                calendar.get(Calendar.DAY_OF_MONTH) + " " +
                months[calendar.get(Calendar.MONTH)]
        holder.headerText.text = date
        calendar.add(Calendar.DAY_OF_MONTH, -getDayIncrement(lesson))
        return headerView
    }

    override fun getHeaderId(position: Int): Long {
        val lesson = mLessons!![position]
        return (lesson.dayOfWeek + lesson.numberOfWeek * 7).toLong()
    }

    override fun getCount(): Int = mLessons!!.size

    override fun getItem(position: Int): Any = mLessons!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var lessonView: View
        val holder: ViewHolder

        if (convertView == null) {
            lessonView = mInflater.inflate(R.layout.schedule_view, parent, false)
            holder = ViewHolder(lessonView)
            lessonView.tag = holder
        } else {
            lessonView = convertView
            holder = lessonView.tag as ViewHolder
        }

        val lesson = mLessons!![position]
        with (holder) {
            time.setImageResource(when(lesson.number){
                1 -> R.drawable.lesson1
                2 -> R.drawable.lesson2
                3 -> R.drawable.lesson3
                4 -> R.drawable.lesson4
                5 -> R.drawable.lesson5
                6 -> R.drawable.lesson6
                7 -> R.drawable.lesson7
                else -> R.drawable.lesson8
            })
            cabinet.text = lesson.cabinet
            val stringTemplate = view.context.getString(R.string.pair)
            lessonNumber.text = String.format(stringTemplate, lesson.number)
            ownerName.text = if (mIsTeacher) lesson.group.name else lesson.teacher.name
            lessonName.text = lesson.name
        }

        return lessonView
    }

    private val daysOfWeek: Array<String>
        get() {
            if (mDaysOfWeek == null)
                mDaysOfWeek = mContext.resources.getStringArray(R.array.short_days_of_week)
            return mDaysOfWeek!!
        }

    private val months: Array<String>
        get() {
            if (mMonths == null)
                mMonths = mContext.resources.getStringArray(R.array.months)
            return mMonths!!
        }

    private fun getDayIncrement(lesson: Lesson): Int =
            (lesson.numberOfWeek - 1) * 7 + (lesson.dayOfWeek - 1)

    private class HeaderViewHolder(context: Context, itemView: View) {
        var headerText: TextView

        init {
            headerText = itemView.findViewById(R.id.headerTitle) as TextView
            setStyle(context)
        }

        fun setStyle(context: Context) {
            headerText.typeface = FontUtil.getMedium(context)
        }
    }

    inner class ViewHolder(itemView: View) {

        var view = itemView

        lateinit var lessonName: TextView
        lateinit var ownerName: TextView
        lateinit var cabinet: TextView
        lateinit var lessonNumber: TextView
        var time: ImageView

        init {
            time = itemView.findViewById(R.id.lessonName) as ImageView

            with (itemView.find<View>(R.id.teacher)) {
                lessonName = find<TextView>(R.id.lessonName)
                ownerName = find<TextView>(R.id.teacher)
                cabinet = find<TextView>(R.id.cabinet)
                lessonNumber = find<TextView>(R.id.lessonNumber)
            }
            setStyle()
        }

        fun setStyle(){
            ownerName.typeface = FontUtil.getMedium(mContext)
        }
    }
}
