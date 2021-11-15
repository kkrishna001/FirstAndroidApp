package com.example.notes_app

import android.annotation.SuppressLint
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class Notes:Serializable {
    var title:String="title"
    var desc:String="description"
    lateinit var time:String;
    init {
        val sdf= SimpleDateFormat("dd/M hh:mm:ss")
        val currentDate=sdf.format(Date());
        time=currentDate;
    }
}