package com.example.notes_app.util


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


open class ApplicationContextUtil : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


}