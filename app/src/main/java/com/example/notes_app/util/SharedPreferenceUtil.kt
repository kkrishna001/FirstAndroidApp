package com.example.notes_app.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREF = "sharedPrefs"
        private var sharedPreference: SharedPreferenceUtil? = null
        fun getInstance(context: Context): SharedPreferenceUtil? {
            if (sharedPreference == null) {
                sharedPreference = SharedPreferenceUtil(context)
            }
            return sharedPreference
        }
    }

    fun putString(key: String?, value: String?) {
        val prefsEditor = sharedPreferences.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }
}