package com.example.firstAndroidApp.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstAndroidApp.data.room.Notes
import com.example.firstAndroidApp.databinding.ActivityNotesHandlerBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesHandler : AppCompatActivity() {


    companion object {
        private const val NOTE_KEY = "NOTE_KEY"
        private const val DATE_FORMAT = "dd/M hh:mm:ss"
    }

    private lateinit var viewBinding: ActivityNotesHandlerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNotesHandlerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setup()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setup() {

        viewBinding.submitNotesButton.setOnClickListener {
            val resultsIntent2 = Intent()

            val simpleDateFormat = SimpleDateFormat(DATE_FORMAT)
            val currentDate = simpleDateFormat.format(Date())

            val note = Notes(
                0,
                viewBinding.notesTitle.text.toString(),
                viewBinding.notesDes.text.toString(),
                currentDate
            )

            resultsIntent2.putExtra(NOTE_KEY, note)
            setResult(RESULT_OK, resultsIntent2)
            finish()
        }
    }

}