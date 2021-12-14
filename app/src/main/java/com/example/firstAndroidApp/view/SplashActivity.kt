package com.example.firstAndroidApp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.example.firstAndroidApp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        lifecycleScope.launch{
            delay(3000)
            callMainActivity()
        }
    }

    private fun callMainActivity(){
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
    }
}