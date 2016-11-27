package ru.ulstu_team.ulstuschedule.util

import android.app.Activity
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * Created by maxmr on 11/26/2016.
 */

fun Activity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(@StringRes text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}