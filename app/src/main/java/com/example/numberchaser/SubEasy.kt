package com.example.numberchaser

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import kotlin.random.Random

class SubEasy: AppCompatActivity() {


    private val totalRounds =
        3                                             //  <== Total nung Rounds na meron sa kada level
    private var currentRound = totalRounds
    private val sequence = ArrayList<Int>()
    private var currentIndex = 0
    private lateinit var correctRoundsTextView: TextView
    private lateinit var numberTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var submitButton: ImageButton
    private lateinit var startButton: ImageButton
    private lateinit var restartButton: ImageButton

    private lateinit var seeResultsButton: ImageButton
    private lateinit var gameOver: ImageView
    private lateinit var playAgain: ImageButton
    private lateinit var exitButton: ImageButton
    private lateinit var roundsTextView: TextView
    private lateinit var firstNumberTextView: TextView


    private lateinit var countDownTimer: CountDownTimer
    private lateinit var timerView: TextView
    private lateinit var editTextCountDownTimer: CountDownTimer

    private val roundDuration: Long = 30000 // or your desired duration
    private var remainingTime: Long = roundDuration
    private val editTextCountDownDuration: Long = 10000 // 5 seconds or your desired duration


    private var correctRounds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sub_easy)

        numberTextView = findViewById(R.id.numberTextView)
        answerEditText = findViewById(R.id.answerEditText)
        submitButton = findViewById(R.id.submitButton)
        startButton = findViewById(R.id.startButton)
        exitButton = findViewById(R.id.exitButton)
        restartButton = findViewById(R.id.restartButton)
        roundsTextView = findViewById(R.id.roundsTextView)
        seeResultsButton = findViewById(R.id.seeResultsButton)
        correctRoundsTextView = findViewById(R.id.correctRoundsTextView)
        gameOver = findViewById(R.id.gameOverImage)
        firstNumberTextView = findViewById(R.id.firstNumberTextView)
        playAgain = findViewById(R.id.playAgainButton)

        timerView = findViewById(R.id.timerTextView)

        startButton.visibility = View.VISIBLE
        submitButton.visibility = View.GONE
        restartButton.visibility = View.GONE
        roundsTextView.text = "Rounds: $currentRound"
        seeResultsButton.visibility = View.GONE
        correctRoundsTextView.visibility = View.GONE

        exitButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        startButton.setOnClickListener {
            startGame()
            startTimer()
        }

        submitButton.setOnClickListener {
            checkAnswer()
        }

        restartButton.setOnClickListener {
            restartGame()
        }

        seeResultsButton.setOnClickListener {
            showResults()
        }
        playAgain.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }

    private fun startTimer() {

        startButton.visibility = View.INVISIBLE
    }

    private fun startGame() {
        startButton.visibility = View.GONE
        submitButton.visibility = View.GONE
        restartButton.visibility = View.GONE
        seeResultsButton.visibility = View.GONE

        remainingTime = roundDuration
        timerView.text = ""

        if (currentRound > 0) {
            sequence.clear()

            val initialNumber =
                30                                                  // first num na mag a-appear
            firstNumberTextView.text = initialNumber.toString()
            firstNumberTextView.visibility = View.VISIBLE

            Handler().postDelayed({
                firstNumberTextView.visibility = View.GONE


                var lastNumber = -1

                for (i in 1..3) {                                           // meaning 5 random number sa sequence
                    var nextNumber: Int
                    do {
                        nextNumber =
                            Random.nextInt(1, 6)               // range nung sequence number
                    } while (sequence.isNotEmpty() && (nextNumber == lastNumber || isConsecutive(
                            sequence.last(),
                            nextNumber
                        ))
                    )

                    sequence.add(nextNumber)
                    lastNumber = nextNumber
                }

                currentIndex = 0
                displayNextNumber()
                currentRound--
                roundsTextView.text = "Rounds: $currentRound"

                Handler().postDelayed({
                    answerEditText.visibility = View.VISIBLE
                    submitButton.visibility = View.VISIBLE
                    startEditTextTimer() // Start the 5-second countdown for EditText
                }, 1000 * sequence.size.toLong() + 6000)
            }, 3000)
        } else {
            seeResultsButton.visibility = View.VISIBLE
        }
        updateRoundTextViews()
    }


    private fun isConsecutive(prevNumber: Int, nextNumber: Int): Boolean {
        return nextNumber == prevNumber + 1 || nextNumber == prevNumber - 1
    }

    private fun startEditTextTimer() {
        editTextCountDownTimer = object : CountDownTimer(editTextCountDownDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update UI to show the remaining time for editText
                val secondsRemaining = millisUntilFinished / 1000
                timerView.text = "$secondsRemaining seconds"
            }

            override fun onFinish() {
                timerView.text = "Time's up."
                // Handle the end of the countdown, e.g., hide EditText
                answerEditText.visibility = View.GONE
                checkAnswer() // Automatically check the answer when time's up
            }
        }

        if (answerEditText.visibility == View.VISIBLE) {
            editTextCountDownTimer.start() // Start the timer when EditText is visible
        }
    }


    private fun displayNextNumber() {
        if (currentIndex < sequence.size) {
            val currentNumber = sequence[currentIndex]
            numberTextView.text = currentNumber.toString()

            Handler().postDelayed(
                {
                    currentIndex++
                    displayNextNumber()
                },
                3000
            )                                       // eto yung deley sa pag-appear nung mga numbers
        } else {
            numberTextView.text = "What's the answer"
        }
    }

    private fun checkAnswer() {
        val userAnswer = answerEditText.text.toString().toIntOrNull()
        val initialNumber = firstNumberTextView.text.toString().toIntOrNull()

        answerEditText.visibility = View.GONE
        editTextCountDownTimer.cancel()

        if (userAnswer != null && initialNumber != null) {
            val correctAnswer = initialNumber - sequence.sum()

            if (userAnswer == correctAnswer) {
                numberTextView.text = "       Correct! \nThe answer is $correctAnswer"
                correctRounds++
            } else {
                numberTextView.text = "The answer is $correctAnswer"
            }
        } else {
            numberTextView.text = "   Oh no! Enter \n a valid number."
        }

        answerEditText.text.clear()

        if (currentRound > 0) {
            startButton.visibility = View.GONE
            submitButton.visibility = View.GONE
            restartButton.visibility = View.VISIBLE
            seeResultsButton.visibility = View.GONE
        } else {
            startButton.visibility = View.GONE
            submitButton.visibility = View.GONE
            restartButton.visibility = View.GONE
            seeResultsButton.visibility = View.VISIBLE
        }
    }

        private fun restartGame() {
            startButton.visibility = View.VISIBLE
            submitButton.visibility = View.GONE
            restartButton.visibility = View.GONE
            seeResultsButton.visibility = View.GONE
            answerEditText.visibility = View.GONE

            if (::countDownTimer.isInitialized && countDownTimer != null) {
                countDownTimer.cancel()
            }

            if (::editTextCountDownTimer.isInitialized && editTextCountDownTimer != null) {
                editTextCountDownTimer.cancel()
            }

            if (currentRound == 0) {
                roundsTextView.visibility = View.GONE
                seeResultsButton.visibility = View.VISIBLE
            } else {
                roundsTextView.text = "Rounds: $currentRound"
            }

            numberTextView.text = "Let's Start"
            answerEditText.text.clear()
            seeResultsButton.visibility = View.GONE
        }

        private fun updateRoundTextViews() {
            roundsTextView.text = "Rounds: $currentRound"
            correctRoundsTextView.text = "You've got $correctRounds out of ${totalRounds}"
        }

        private fun showResults() {
            numberTextView.visibility = View.GONE
            roundsTextView.visibility = View.GONE
            playAgain.visibility = View.VISIBLE
            startButton.visibility = View.GONE
            exitButton.visibility = View.VISIBLE
            submitButton.visibility = View.GONE
            restartButton.visibility = View.GONE
            seeResultsButton.visibility = View.GONE
            answerEditText.visibility = View.GONE
            correctRoundsTextView.visibility = View.VISIBLE
            correctRoundsTextView.text = "You've got $correctRounds out of ${totalRounds}"

            val gifImageView: ImageView = findViewById(R.id.gameOverImage)
            Glide.with(this).asGif().load(R.drawable.play_again2).into(gifImageView)
            gameOver.visibility = View.VISIBLE

        }
    }

