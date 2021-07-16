package com.example.clickthecolor

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        lateinit var value: String
        val extras = intent.extras
        if (extras != null) {
            value = extras.getString("key")!!
            //The key argument here must match that used in the other activity
        }
        val score = findViewById<TextView>(R.id.score)
        val scoreText = findViewById<TextView>(R.id.score_text)
        if (value == "0") {
            scoreText.text = "YOU LOSE!"
            score.text = ""
        } else {
            scoreText.text = "Score:"
            score.text = value
        }


        val restart = findViewById<Button>(R.id.restart)
        restart.setOnClickListener{
            finish()
        }
    }
}