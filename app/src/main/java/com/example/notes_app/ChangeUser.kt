package com.example.notes_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ChangeUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user)

        val intent:Intent= intent;

        val name:EditText=findViewById(R.id.editusername)
        val email:EditText=findViewById(R.id.useremail);

        name.setText(intent.getStringExtra("NAME_KEY"))
        email.setText(intent.getStringExtra("EMAIL_KEY"))

        val bttn:Button=findViewById(R.id.submitbutton);

        bttn.setOnClickListener{
            val resultsIntent=Intent()
            resultsIntent.putExtra("NAME_KEY",name.text.toString())
            resultsIntent.putExtra("EMAIL_KEY",email.text.toString())
            setResult(RESULT_OK,resultsIntent)
            finish();
        }
    }
}