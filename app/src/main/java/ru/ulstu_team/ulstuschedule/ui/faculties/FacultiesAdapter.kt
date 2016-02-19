package ru.ulstu_team.ulstuschedule.ui.faculties

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Faculty
import ru.ulstu_team.ulstuschedule.ui.cathedries.CathedriesActivity

class FacultiesAdapter : RecyclerView.Adapter<FacultiesAdapter.ViewHolder>() {

    private lateinit var mFaculties: List<Faculty>

    fun setFaculties(faculties: List<Faculty>) {
        mFaculties = faculties
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.simple_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val faculty = mFaculties[position]
        holder.name.text = faculty.name
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CathedriesActivity::class.java)
                    .putExtra("FacultyId", faculty.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mFaculties.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var name: TextView

        init {
            name = itemView.findViewById(R.id.name) as TextView
        }
    }
}
