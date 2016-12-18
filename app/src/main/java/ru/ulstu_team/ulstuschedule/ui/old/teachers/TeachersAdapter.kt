package ru.ulstu_team.ulstuschedule.ui.old.teachers

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Teacher
import ru.ulstu_team.ulstuschedule.ui.schedule.ScheduleActivity


class TeachersAdapter : RecyclerView.Adapter<TeachersAdapter.ViewHolder>() {

    private var mTeachers: List<Teacher>? = null

    fun setTeachers(teachers: List<Teacher>) {
        mTeachers = teachers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.simple_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val teacher = mTeachers!![position]
        holder.name.text = teacher.name
        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ScheduleActivity::class.java)
                    .putExtra("owner", "teacher")
                    .putExtra("ownerName", teacher.name)
                    .putExtra("id", teacher.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mTeachers!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView

        init {
            name = itemView.findViewById(R.id.name) as TextView
        }
    }
}
