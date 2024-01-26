package com.example.numberchaser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val easyButton = findViewById<ImageButton>(R.id.easyBtn)
        easyButton.setOnClickListener {
            val intent = Intent(this, AddEasy::class.java)
            startActivity(intent)
        }
        val normalButton = findViewById<ImageButton>(R.id.normalBtn)
        normalButton.setOnClickListener {
            val intent = Intent(this, AddNormal::class.java)
            startActivity(intent)
        }
        val hardButton = findViewById<ImageButton>(R.id.hardBtn)
        hardButton.setOnClickListener {
            val intent = Intent(this, AddHard::class.java)
            startActivity(intent)
        }
    }
}