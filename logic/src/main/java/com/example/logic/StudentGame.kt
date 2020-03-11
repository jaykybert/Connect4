package com.example.logic

import com.example.lib.GameInterface
import kotlin.IllegalArgumentException

class StudentGame (columns: Int = 10, rows: Int = 10): GameInterface {

    override val mColumns: Int = columns
    override val mRows: Int = rows

    // Set up two-dimensional array of integer, of size column x rows
    // and fill it with zeroes.
    override var mData: Array<IntArray> = Array(columns) { IntArray(rows) {0} }

    // Not specified in the interface - just to show that other things can be added.
    var playerTurn: Int = 1

    /*
    init {
        // Place a couple of random tokens to test the user interface.
        mData[5][5] = 1
        mData[6][3] = 2
    } */

    // Returns the state of the game board at a specified column and row number.
    override fun getToken(column: Int, row: Int): Int {
        return mData[column][row]
    }

    // Changes the contents of the game board at a specified row and column.
    override fun playToken(column: Int, player: Int): Boolean {
        if (player <= 0) {
            throw IllegalArgumentException("Player numbers start with 1.")
        }
        // Alternate player turns.
        playerTurn = if (playerTurn == 1) { 2 } else 1

        for (row in mRows - 1 downTo 0) {
            if (mData[column][row] == 0) {
                mData[column][row] = player
                return true
            }
        }
        return false // Illegal move.
    }
}
