package ru.ulstu_team.ulstuschedule.ui.main

import android.support.design.widget.BottomNavigationView
import android.widget.LinearLayout
import org.jetbrains.anko.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.util.bottomNavigationView
import ru.ulstu_team.ulstuschedule.util.colorStateList

class MainActivityUI : AnkoComponent<MainActivity> {
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