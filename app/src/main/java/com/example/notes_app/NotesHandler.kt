package com.example.notes_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class NotesHandler : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_handler)

        val tit=findViewById<EditText>(R.id.notesTitle)
        val des=findViewById<EditText>(R.id.notesDes)

        val bttn=findViewById<Button>(R.id.submitnotesbutton)
        bttn.setOnClickListener {
            val resultsIntent2= Intent()
            val note=Notes()
            note.title=tit.text.toString();
            note.desc=des.text.toString();

            resultsIntent2.putExtra("NOTE_KEY",note)
            setResult(RESULT_OK,resultsIntent2)
            finish()
        }
    }

}