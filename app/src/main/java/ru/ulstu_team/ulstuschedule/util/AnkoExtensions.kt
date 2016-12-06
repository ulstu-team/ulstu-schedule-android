package ru.ulstu_team.ulstuschedule.util

import android.support.design.widget.BottomNavigationView
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

fun ViewManager.bottomNavigationView(theme: Int = 0)
        = bottomNavigationView(theme) {}
inline fun ViewManager.bottomNavigationView(theme: Int = 0, init: BottomNavigationView.() -> Unit)
        = ankoView(::BottomNavigationView, theme, init)

