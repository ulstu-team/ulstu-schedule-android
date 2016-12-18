package ru.ulstu_team.ulstuschedule.util

import android.app.Activity
import android.app.Fragment
import android.support.annotation.StringRes
import android.widget.Toast

fun Activity.presentFragment(containerId: Int, tag: String, fragment: () -> Fragment) {
    val presentingFragment = fragmentManager.findFragmentByTag(tag) ?: fragment()
    fragmentManager.beginTransaction()
        .replace(containerId, presentingFragment, tag)
        .commit()
}