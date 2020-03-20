package com.example.connect4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.NumberFormatException


class StartGame: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val columns = intent.getStringExtra(COLUMN_CHOICE)
        val rows = intent.getStringExtra(ROW_CHOICE)

        if(columns != null && rows != null)
            try {
                val c = columns.toInt()
                val r = rows.toInt()
                val gameView = GameView(c, r, this)
                setContentView(gameView)
            }
            catch(exc: NumberFormatException) {
                val gameView = GameView(7, 7, this)
                setContentView(gameView)
            }
        else {
            val gameView = GameView(7, 7, this)
            setContentView(gameView)
        }
    }
}