package com.example.notes_app.uis.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.notes_app.R
import com.example.notes_app.data.room.Notes
import java.text.SimpleDateFormat
import java.util.*

class NotesHandler : AppCompatActivity() {


    companion object{
        private const val NOTE_KEY="NOTE_KEY"
        private const val DATE_FORMAT="dd/M hh:mm:ss"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_handler)

        setup()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setup(){
        val title=findViewById<EditText>(R.id.notesTitle)
        val description=findViewById<EditText>(R.id.notesDes)

        val bttn=findViewById<Button>(R.id.submitNotesButton)
        bttn.setOnClickListener {
            val resultsIntent2= Intent()

            val simpleDateFormat= SimpleDateFormat(DATE_FORMAT)
            val currentDate=simpleDateFormat.format(Date())

            val note= Notes(0,title.text.toString(),description.text.toString(),currentDate);
            resultsIntent2.putExtra(NOTE_KEY,note)
            setResult(RESULT_OK,resultsIntent2)
            finish()
        }
    }

}