package ru.ulstu_team.ulstuschedule.data.local

import android.content.Context
import java.util.*
import javax.inject.Inject

class PrefsManager
@Inject
constructor(private val context: Context) {

    fun getString(key: String): String {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        return prefs.getString(key, "")
    }

    fun putString(key: String, value: String) {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getInt(key: String): Int {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        return prefs.getInt(key, 0)
    }

    fun putInt(key: String, value: Int) {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getLong(key: String): Long {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        return prefs.getLong(key, 0)
    }

    fun putLong(key: String, value: Long) {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        val editor = prefs.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getStringSet(key: String): Set<String> {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        return prefs.getStringSet(key, LinkedHashSet<String>())
    }

    fun putStringSet(key: String, value: Set<String>) {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        val editor = prefs.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    operator fun contains(key: String): Boolean {
        val prefs = context.getSharedPreferences(FILE_NAME, 0)
        return prefs.contains(key)
    }

    fun delete(key: String) {
        val preferences = context.getSharedPreferences(FILE_NAME, 0)
        preferences.edit().remove(key).apply()
    }

    companion object {
        private val FILE_NAME = "AppCache"
    }
}