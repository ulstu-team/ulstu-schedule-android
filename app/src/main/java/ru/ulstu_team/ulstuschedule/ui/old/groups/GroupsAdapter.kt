package ru.ulstu_team.ulstuschedule.ui.old.groups

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Group
import ru.ulstu_team.ulstuschedule.ui.schedule.ScheduleActivity

class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {

    private var mGroups: List<Group>? = null

    fun setGroups(groups: List<Group>) {
        mGroups = groups
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.simple_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = mGroups!![position]
        holder.name.text = group.name
        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ScheduleActivity::class.java)
                    .putExtra("owner", "group")
                    .putExtra("ownerName", group.name)
                    .putExtra("id", group.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mGroups!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView

        init {
            name = itemView.findViewById(R.id.name) as TextView
        }
    }
}
