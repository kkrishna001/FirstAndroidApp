package com.example.notes_app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class NotesHandler : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_handler)

        val tit=findViewById<EditText>(R.id.notesTitle)
        val des=findViewById<EditText>(R.id.notesDes)

        val bttn=findViewById<Button>(R.id.submitnotesbutton)
        bttn.setOnClickListener {
            val resultsIntent2= Intent()

            val sdf= SimpleDateFormat("dd/M hh:mm:ss")
            val currentDate=sdf.format(Date())

            val note=Notes(0,tit.text.toString(),des.text.toString(),currentDate);
            resultsIntent2.putExtra("NOTE_KEY",note)
            setResult(RESULT_OK,resultsIntent2)
            finish()


        }
    }

}