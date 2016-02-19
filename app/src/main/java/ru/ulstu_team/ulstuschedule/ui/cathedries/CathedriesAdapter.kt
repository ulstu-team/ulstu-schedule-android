package ru.ulstu_team.ulstuschedule.ui.cathedries

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Cathedra

class CathedriesAdapter : RecyclerView.Adapter<CathedriesAdapter.ViewHolder>() {

    private lateinit  var mCathedries: List<Cathedra>

    fun setCathedries(cathedries: List<Cathedra>) {
        mCathedries = cathedries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cathedra = mCathedries[position]
        holder.name.text = cathedra.name
    }

    override fun getItemCount(): Int {
        return mCathedries.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView

        init {
            name = itemView.findViewById(R.id.name) as TextView
        }
    }
}
