package com.example.numberchaser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val subEasyButton = findViewById<ImageButton>(R.id.subEasyBtn)
        subEasyButton.setOnClickListener {
            val intent = Intent(this, SubEasy::class.java)
            startActivity(intent)
        }
        val subNormalButton = findViewById<ImageButton>(R.id.subNormalBtn)
        subNormalButton.setOnClickListener {
            val intent = Intent(this, SubNormal::class.java)
            startActivity(intent)
        }
        val subHardButton = findViewById<ImageButton>(R.id.subHardBtn)
        subHardButton.setOnClickListener {
            val intent = Intent(this, SubHard::class.java)
            startActivity(intent)
        }
    }
}