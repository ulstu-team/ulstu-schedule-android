package ru.ulstu_team.ulstuschedule.util

import android.app.Activity
import android.app.Fragment
import android.support.annotation.StringRes
import android.widget.Toast

fun Activity.presentFragment(containerId: Int, tag: String, fragment: () -> Fragment) {
    val currentFragment = fragmentManager.findFragmentByTag(tag)
    if (currentFragment == null) {
        fragmentManager.beginTransaction()
                .add(containerId, fragment(), tag)
                .commit()
    } else {
        fragmentManager.beginTransaction()
                .show(currentFragment)
                .commit()
    }
}