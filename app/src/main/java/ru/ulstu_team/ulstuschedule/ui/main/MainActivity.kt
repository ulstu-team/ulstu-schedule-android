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
import ru.ulstu_team.ulstuschedule.util.showToast

class MainActivity : AppCompatActivity() {
    private val CONTENT_CONTAINER_ID = 0x00000000000001
    private val BOTTOM_NAVIGATION_BAR_ID = 0x00000000000002

    private lateinit var contentContainer: LinearLayout
    private lateinit var bottomNavigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        configureBottomNavigationBar()

        val scheduleFragment = fragmentManager.findFragmentByTag(ScheduleFragment.TAG)
        if (scheduleFragment == null) {
            fragmentManager.beginTransaction().add(CONTENT_CONTAINER_ID,
                    ScheduleFragment.newInstance(),
                    ScheduleFragment.TAG)
                    .commit()
        } else {
            fragmentManager.beginTransaction()
                    .show(scheduleFragment)
                    .commit()
        }
    }

    private fun setContentView() {
        relativeLayout {
            lparams {
                width = matchParent
                height = matchParent
            }
            bottomNavigationBar = bottomNavigationView {
                id = BOTTOM_NAVIGATION_BAR_ID
                backgroundResource = R.color.colorPrimary
                itemBackgroundResource = R.color.colorPrimary
                itemTextColor = colorStateList(R.color.bottom_navigation_text_color)
                itemIconTintList = colorStateList(R.color.bottom_navigation_text_color)
                inflateMenu(R.menu.bottom_navigation_menu)
            }.lparams {
                width = matchParent
                alignParentBottom()
            }
            addView(bottomNavigationBar)
            contentContainer = verticalLayout {
                id = CONTENT_CONTAINER_ID
            }.lparams {
                width = matchParent
                height = matchParent
                above(BOTTOM_NAVIGATION_BAR_ID)
            }
        }
    }

    private fun configureBottomNavigationBar() {
        bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_my_schedule ->
                    showToast(R.string.bottom_bar_my_schedule)
                R.id.action_favorites ->
                    showToast(R.string.bottom_bar_favorite)
                R.id.action_settings ->
                    showToast(R.string.bottom_bar_settings)
            }
            false
        }
    }
}
