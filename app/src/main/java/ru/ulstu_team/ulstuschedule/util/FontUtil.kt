package ru.ulstu_team.ulstuschedule.util

import android.content.Context
import android.graphics.Typeface
import android.util.Log

object FontUtil {

    private val TAG = "FontUtil"

    private fun getFont(context: Context, fontName: String): Typeface {
        var font = fontName
        try {
            val pathBegin = "fonts/"
            if (!font.startsWith(pathBegin))
                font = pathBegin + font
            return Typeface.createFromAsset(context.assets, font)
        } catch (ex: Exception) {
            Log.e(TAG, "getFont: ", ex)
        }

        return Typeface.DEFAULT
    }

    fun getMedium(context: Context) = getFont(context, "Roboto-Medium.ttf")
}
