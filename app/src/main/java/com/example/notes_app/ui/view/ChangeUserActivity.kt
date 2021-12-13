package com.example.notes_app.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.notes_app.R
import com.example.notes_app.databinding.ActivityChangeUserBinding

class ChangeUser : AppCompatActivity() {

    companion object {
        private const val NAME_KEY = "NAME_KEY"
        private const val EMAIL_KEY = "EMAIL_KEY"
    }

    private lateinit var viewBinding: ActivityChangeUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChangeUserBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setup()
    }

    private fun setup() {
        val intent: Intent = intent;

        viewBinding.editusername.setText(intent.getStringExtra(NAME_KEY))
        viewBinding.useremail.setText(intent.getStringExtra(EMAIL_KEY))

        val bttn: Button = findViewById(R.id.submitbutton);
        bttn.setOnClickListener {
            val resultsIntent = Intent()
            resultsIntent.putExtra(NAME_KEY, viewBinding.editusername.text.toString())
            resultsIntent.putExtra(EMAIL_KEY, viewBinding.useremail.text.toString())
            setResult(RESULT_OK, resultsIntent)
            finish();
        }
    }
}