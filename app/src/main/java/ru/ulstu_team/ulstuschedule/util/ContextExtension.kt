package ru.ulstu_team.ulstuschedule.util

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.support.annotation.ColorRes

fun Context.color(@ColorRes color: Int) : Int {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        @Suppress("DEPRECATION")
        return resources.getColor(color)
    } else {
        return resources.getColor(color, theme)
    }
}

fun Context.colorStateList(@ColorRes colorStateList: Int) : ColorStateList {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        @Suppress("DEPRECATION")
        return resources.getColorStateList(colorStateList)
    } else {
        return resources.getColorStateList(colorStateList, theme)
    }
}

fun Context.getColorResource(@ColorRes color: Int) : Int {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return resources.getColor(color)
    } else{
        return resources.getColor(color, this.theme)
    }
}