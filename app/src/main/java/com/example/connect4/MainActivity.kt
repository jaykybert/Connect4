package com.example.connect4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

const val COLUMN_CHOICE = "com.example.connect4.COLUMN"
const val ROW_CHOICE = "com.example.connect4.ROW"


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Called when the user taps the Play button.
    fun playGame(view: View) {
        val columnInput = findViewById<TextView>(R.id.input_column).text.toString()
        val rowInput = findViewById<TextView>(R.id.input_row).text.toString()
        val intent = Intent(this, StartGame::class.java).apply {
            putExtra(COLUMN_CHOICE, columnInput)
            putExtra(ROW_CHOICE, rowInput)
        }
        startActivity(intent)
    }
}
