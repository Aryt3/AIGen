package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val switchButton2 = findViewById<Button>(R.id.switchButton2)
        switchButton2.setOnClickListener {
            // Switch back to MainActivity
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish the current activity
        }

        // Rest of your code...
    }

    // Rest of your code...
}