package com.example.notes_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.notes_app.data.room.Notes

class EditNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val intent:Intent=intent
        val position= intent.getIntExtra("EDITPOS_KEY",0);
        val note=intent.getSerializableExtra("EDITNOTE_KEY") as Notes;
        val tit=findViewById<EditText>(R.id.notesTitle2)
        val des=findViewById<EditText>(R.id.notesDes2)
        tit.setText(note.title)
        des.setText(note.desc)
        val bttn=findViewById<Button>(R.id.submitnotesbutton2)

        bttn.setOnClickListener {
            val resultsIntent2= Intent()

            note.title=tit.text.toString();
            note.desc=des.text.toString();
            resultsIntent2.putExtra("EDITNOTE_KEY",note)
            resultsIntent2.putExtra("EDITPOS_KEY",position)
            setResult(RESULT_OK,resultsIntent2)
            finish()
        }

    }
}