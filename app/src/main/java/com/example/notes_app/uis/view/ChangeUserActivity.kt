package com.example.notes_app.uis.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.notes_app.R

class ChangeUser : AppCompatActivity() {

    companion object{
        private const val NAME_KEY = "NAME_KEY"
        private const val EMAIL_KEY = "EMAIL_KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user)

        setup()
    }

    private fun setup(){
        val intent:Intent= intent;

        val userName:EditText=findViewById(R.id.editusername)
        val userEmail:EditText=findViewById(R.id.useremail);
        userName.setText(intent.getStringExtra(NAME_KEY))
        userEmail.setText(intent.getStringExtra(EMAIL_KEY))

        val bttn:Button=findViewById(R.id.submitbutton);
        bttn.setOnClickListener{
            val resultsIntent=Intent()
            resultsIntent.putExtra(NAME_KEY,userName.text.toString())
            resultsIntent.putExtra(EMAIL_KEY,userEmail.text.toString())
            setResult(RESULT_OK,resultsIntent)
            finish();
        }
    }
}