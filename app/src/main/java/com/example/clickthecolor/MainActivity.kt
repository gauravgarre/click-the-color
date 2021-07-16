package com.example.clickthecolor

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val colorText = findViewById<TextView>(R.id.color_text)
        colorText.setTextColor(getRandomColor())
        colorText.text = getRandomColorText()
        val option = findViewById<TextView>(R.id.option)
        option.text = getRandomOption()

        val buttonRed = findViewById<Button>(R.id.button_red)
        val buttonYellow = findViewById<Button>(R.id.button_pink)
        val buttonGreen = findViewById<Button>(R.id.button_green)
        val buttonBlue = findViewById<Button>(R.id.button_blue)
        val buttonGray = findViewById<Button>(R.id.button_gray)
        val buttonBlack = findViewById<Button>(R.id.button_black)
        val score = findViewById<TextView>(R.id.score)
        val buttons: List<Button> =
            listOf(buttonRed, buttonYellow, buttonGreen,
                    buttonBlue, buttonGray, buttonBlack)

        val mCountDownTimer: CountDownTimer
        var i = 0

        val mProgressBar: ProgressBar = findViewById<View>(R.id.progressbar) as ProgressBar
        mProgressBar.progress = i
        mCountDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.v("Log_tag", "Tick of Progress$i$millisUntilFinished")
                i++
                mProgressBar.progress = i as Int * 100 / (30000 / 1000)
            }

            override fun onFinish() {
                //Do what you want
                val intent = Intent(applicationContext, MainActivity2::class.java)
                intent.putExtra("key", score.text)
                startActivity(intent)
            }
        }
        mCountDownTimer.start()

        for (button in buttons) {
            button.setOnClickListener{ checkMatch(button, colorText, option, mCountDownTimer)}
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun getRandomOption() : String {
        val randomInt = (1..2).random()
        return when (randomInt) {
            1 -> "TEXT"
            else -> "COLOR"
        }
    }

    private fun getRandomColor() : Int {
        val randomInt = (1..6).random()
        return when (randomInt) {
            1 -> Color.RED
            2 -> Color.rgb(255, 20, 147)
            3 -> Color.GREEN
            4 -> Color.BLUE
            5 -> Color.GRAY
            else -> Color.BLACK
        }
    }

    private fun getRandomColorText() : String {
        val randomInt = (1..6).random()
        return when (randomInt) {
            1 -> "RED"
            2 -> "PINK"
            3 -> "GREEN"
            4 -> "BLUE"
            5 -> "GRAY"
            else -> "BLACK"
        }
    }

    private fun colorToText(int: Int) : String {
        return when (int) {
            Color.RED -> "RED"
            Color.rgb(255, 20, 147) -> "PINK"
            Color.GREEN -> "GREEN"
            Color.BLUE -> "BLUE"
            Color.GRAY -> "GRAY"
            else -> "BLACK"
        }
    }

    private fun checkMatch(button: Button, colorText: TextView, option: TextView, countdown: CountDownTimer) {
        val score = findViewById<TextView>(R.id.score)
        if (option.text == "COLOR" && button.text == colorToText(colorText.currentTextColor)) {
            score.text = (Integer.parseInt(score.text.toString()) + 1).toString()
        } else if (option.text == "TEXT" && button.text == colorText.text) {
            score.text = (Integer.parseInt(score.text.toString()) + 1).toString()
        } else {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.fatality)
            mp.start()
            score.text = 0.toString()
            countdown.cancel()
            countdown.onFinish()
        }
        colorText.setTextColor(getRandomColor())
        colorText.text = getRandomColorText()
        option.text = getRandomOption()
    }

}