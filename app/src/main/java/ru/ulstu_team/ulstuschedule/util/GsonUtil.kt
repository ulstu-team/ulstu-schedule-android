package ru.ulstu_team.ulstuschedule.util

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import io.realm.RealmObject

object GsonUtil {

    private var mGson: Gson? = null

    val gsonInstance: Gson
        get() {
            if (mGson == null) {
                mGson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
                    override fun shouldSkipField(f: FieldAttributes): Boolean {
                        return f.declaringClass == RealmObject::class.java
                    }

                    override fun shouldSkipClass(clazz: Class<*>): Boolean {
                        return false
                    }
                }).create()
            }
            return mGson!!
        }
}

