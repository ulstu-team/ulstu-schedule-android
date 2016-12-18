package ru.ulstu_team.ulstuschedule.ui.schedule

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ScheduleFragmentUI : AnkoComponent<ScheduleFragment> {

    lateinit var rvSchedules: RecyclerView

    override fun createView(ui: AnkoContext<ScheduleFragment>) = with(ui) {
        verticalLayout {
            lparams {
                width = matchParent
                height = matchParent
            }
            rvSchedules = recyclerView {
                layoutManager = LinearLayoutManager(ctx)
                setHasFixedSize(true)
            }.lparams {
                width = matchParent
                height = matchParent
            }
        }
    }
}