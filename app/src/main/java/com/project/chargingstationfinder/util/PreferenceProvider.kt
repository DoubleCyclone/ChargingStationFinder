package com.project.chargingstationfinder.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun putString(key: String, value: String) {
        preference.edit().putString(
            key, value
        ).apply()
    }

    fun getString(key: String): String {
        return preference.getString(key, null) ?: ""
    }

    fun putInt(key: String, value: Int) {
        preference.edit().putInt(
            key, value
        ).apply()
    }

    fun getInt(key: String): Int {
        return preference.getInt(key, -1)
    }

    fun putFloat(key: String, value: Float) {
        preference.edit().putFloat(
            key, value
        ).apply()
    }

    fun getFloat(key: String): Float {
        return preference.getFloat(key, -1F)
    }

}