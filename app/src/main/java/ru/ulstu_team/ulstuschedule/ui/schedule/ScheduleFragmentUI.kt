package ru.ulstu_team.ulstuschedule.ui.schedule

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.GridLayout
import android.widget.GridView
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.util.getColorResource

class ScheduleFragmentUI : AnkoComponent<ScheduleFragment> {

    private val TODAY_VIEW_ID = 0x1

    lateinit var rvSchedules: RecyclerView
    lateinit var calendarGrid: GridLayout
    lateinit var tvCurrentDay: TextView
    lateinit var tvCurrentMonth: TextView
    lateinit var tvTodayLessinCount: TextView

    override fun createView(ui: AnkoContext<ScheduleFragment>) = with(ui) {
        verticalLayout {
            lparams { width = matchParent; height = matchParent }

            linearLayout {
                lparams {
                    width = matchParent; height = dip(170)
                }
                backgroundResource = R.drawable.shedule_header_background

                relativeLayout {
                    backgroundDrawable = resources.getDrawable(R.drawable.today_background, ctx.theme)

                    verticalLayout {
                        id = TODAY_VIEW_ID
                        tvCurrentDay = textView {
                            textSize = sp(15).toFloat()
                            textColor = ctx.getColorResource(R.color.white)
                            includeFontPadding = false
                        }
                        tvCurrentMonth = textView {
                            textColor = ctx.getColorResource(R.color.white)
                        }
                    }.lparams {
                        width = wrapContent; height = wrapContent
                        centerInParent()
                    }

                    tvTodayLessinCount = textView {
                        textSize = sp(12).toFloat()
                        textColor = ctx.getColorResource(R.color.white)
                    }.lparams {
                        width = wrapContent; height = wrapContent
                        below(TODAY_VIEW_ID)
                    }
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
                    width = wrapContent; height = wrapContent
                    gravity = Gravity.CENTER
                }
            }

            rvSchedules = recyclerView {
                layoutManager = LinearLayoutManager(ctx)
                setHasFixedSize(true)
            }.lparams { width = matchParent; height = matchParent }
        }
    }
}