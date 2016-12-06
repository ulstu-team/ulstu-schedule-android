package ru.ulstu_team.ulstuschedule.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import org.jetbrains.anko.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.ui.schedule.ScheduleFragment
import ru.ulstu_team.ulstuschedule.util.bottomNavigationView
import ru.ulstu_team.ulstuschedule.util.colorStateList
import ru.ulstu_team.ulstuschedule.util.presentFragment

class MainActivity : AppCompatActivity() {

    private lateinit var ui: MainActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = MainActivityUI()
        ui.setContentView(this)
        configureBottomNavigationBar()

        showSchedule()
    }

    private fun configureBottomNavigationBar() {
        ui.bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_my_schedule ->
                    showSchedule()
                R.id.action_favorites ->
                    toast(R.string.bottom_bar_favorite)
                R.id.action_settings ->
                    toast(R.string.bottom_bar_settings)
            }
            false
        }
    }

    private fun showSchedule() =
            presentFragment(ui.CONTENT_CONTAINER_ID, ScheduleFragment.TAG, { ScheduleFragment.newInstance() })


    private class MainActivityUI : AnkoComponent<MainActivity> {
        val CONTENT_CONTAINER_ID = 0x1
        val BOTTOM_NAVIGATION_BAR_ID = 0x2

        lateinit var contentContainer: LinearLayout
        lateinit var bottomNavigationBar: BottomNavigationView

        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            relativeLayout {
                lparams {
                    width = matchParent
                    height = matchParent
                }
                bottomNavigationBar = bottomNavigationView {
                    id = BOTTOM_NAVIGATION_BAR_ID
                    backgroundResource = R.color.colorPrimary
                    itemBackgroundResource = R.color.colorPrimary
                    itemTextColor = ui.ctx.colorStateList(R.color.bottom_navigation_text_color)
                    itemIconTintList = ui.ctx.colorStateList(R.color.bottom_navigation_text_color)
                    inflateMenu(R.menu.bottom_navigation_menu)
                }.lparams {
                    width = matchParent
                    alignParentBottom()
                }
                contentContainer = verticalLayout {
                    id = CONTENT_CONTAINER_ID
                }.lparams {
                    width = matchParent
                    height = matchParent
                    above(BOTTOM_NAVIGATION_BAR_ID)
                }
            }
        }
    }
}
