package com.example.firstAndroidApp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.firstAndroidApp.util.ApplicationContextUtil.Companion.context

class SharedPreferenceUtil {

    companion object {
        private const val SHARED_PREF = "sharedPrefs"
    }

    private val sharedPreferences: SharedPreferences? =
        context?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    fun putString(key: String?, value: String?) {
        val prefsEditor = sharedPreferences?.edit()
        prefsEditor?.putString(key, value)
        prefsEditor?.apply()
    }

    fun getString(key: String?): String? {
        return if (sharedPreferences != null) {
            sharedPreferences.getString(key, "")
        } else {
            ""
        }
    }
}