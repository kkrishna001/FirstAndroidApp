package com.example.firstAndroidApp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstAndroidApp.data.room.Notes
import com.example.firstAndroidApp.databinding.ActivityEditNoteBinding

class EditNote : AppCompatActivity() {

    companion object {
        private const val EDITNOTE_KEY = "EDITNOTE_KEY"
        private const val EDITPOS_KEY = "EDITPOS_KEY"
    }

    private lateinit var viewBinding: ActivityEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setup()
    }

    private fun setup() {
        val intent: Intent = intent
        val position = intent.getIntExtra(EDITPOS_KEY, 0);
        val note = intent.getSerializableExtra(EDITNOTE_KEY) as Notes;


        viewBinding.notesTitle2.setText(note.title)
        viewBinding.notesDes2.setText(note.desc)

        viewBinding.submitNotesButton2.setOnClickListener {
            val resultsIntent2 = Intent()

            note.title = viewBinding.notesTitle2.text.toString();
            note.desc = viewBinding.notesDes2.text.toString();
            resultsIntent2.putExtra(EDITNOTE_KEY, note)
            resultsIntent2.putExtra(EDITPOS_KEY, position)
            setResult(RESULT_OK, resultsIntent2)
            finish()
        }
    }
}