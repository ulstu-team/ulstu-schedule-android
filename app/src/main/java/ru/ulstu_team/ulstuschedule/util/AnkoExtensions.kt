package ru.ulstu_team.ulstuschedule.util

import android.app.Activity
import android.content.Context
import android.support.design.widget.BottomNavigationView
import org.jetbrains.anko.custom.ankoView

//fun Activity.bottomNavigationView(theme: Int = 0) = bottomNavigationView(theme) {}
//inline fun Activity.bottomNavigationView(theme: Int = 0, init: BottomNavigationView.() -> Unit)
//        = ankoView(::BottomNavigationView, theme, init)

fun Context.bottomNavigationView(theme: Int = 0) = bottomNavigationView(theme) {}
inline fun Context.bottomNavigationView(theme: Int = 0, init: BottomNavigationView.() -> Unit)
        = ankoView(::BottomNavigationView, theme, init)

