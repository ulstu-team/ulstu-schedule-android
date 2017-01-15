package ru.ulstu_team.ulstuschedule.ui.schedule

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.GridLayout
import android.widget.GridView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ru.ulstu_team.ulstuschedule.R

class ScheduleFragmentUI : AnkoComponent<ScheduleFragment> {

    lateinit var rvSchedules: RecyclerView
    lateinit var calendarGrid: GridLayout

    override fun createView(ui: AnkoContext<ScheduleFragment>) = with(ui) {
        verticalLayout {
            lparams { width = matchParent; height = matchParent }

            linearLayout {
                lparams {
                    width = matchParent; height = dip(150)
                }
                backgroundResource = R.drawable.shedule_header_background

                relativeLayout {
                    backgroundDrawable = resources.getDrawable(R.drawable.today_background, ctx.theme)
                }.lparams {
                    width = dip(100); height = dip(100)
                    marginStart = dip(24)
                    marginEnd = dip(24)
                    gravity = Gravity.CENTER_VERTICAL
                }

                calendarGrid = gridLayout {
                    backgroundColor = android.R.color.holo_red_dark
                    columnCount = 7
                }.lparams {
                    width = matchParent; height = matchParent
                }
            }

            rvSchedules = recyclerView {
                layoutManager = LinearLayoutManager(ctx)
                setHasFixedSize(true)
            }.lparams { width = matchParent; height = matchParent }
        }
    }
}