package com.example.notes_app

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "notes_container") data class Notes(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "title") var title:String,
    @ColumnInfo(name = "desc") var desc:String,
    @ColumnInfo(name = "time") val time:String) :Serializable
