package com.example.notes_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.notes_app.data.room.Notes

class EditNote : AppCompatActivity() {

    companion object {
        private const val EDITNOTE_KEY = "EDITNOTE_KEY"
        private const val EDITPOS_KEY = "EDITPOS_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        setup()
    }

    private fun setup() {
        val intent: Intent = intent
        val position = intent.getIntExtra(EDITPOS_KEY, 0);
        val note = intent.getSerializableExtra(EDITNOTE_KEY) as Notes;

        val title = findViewById<EditText>(R.id.notesTitle2)
        val description = findViewById<EditText>(R.id.notesDes2)

        title.setText(note.title)
        description.setText(note.desc)
        val bttn = findViewById<Button>(R.id.submitNotesButton2)

        bttn.setOnClickListener {
            val resultsIntent2 = Intent()

            note.title = title.text.toString();
            note.desc = description.text.toString();
            resultsIntent2.putExtra(EDITNOTE_KEY, note)
            resultsIntent2.putExtra(EDITPOS_KEY, position)
            setResult(RESULT_OK, resultsIntent2)
            finish()
        }
    }
}