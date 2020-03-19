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

    /* Variable which will hold a reference to the function onGameChange
     which is declared in the GameView class. */
    private var onGameChangeListener: GameChangeInterface? = null

    /* Function, which will, when called, store a reference back to the View
     * (the thing listening for a change) in the variable onGameChangeListener.
     * This enables the game class to alert the view class when the game state changes.
     */
    fun setGameChangeListener(listenerImp: GameChangeInterface) {
        onGameChangeListener = listenerImp
    }

    // Call fireGameChange from anywhere in this class which can change the game state
    private fun fireGameChange() {
        onGameChangeListener?.onGameChange(this)
    }

    // Returns the state of the game board at a specified column and row number.
    override fun getToken(column: Int, row: Int): Int {
        return mData[column][row]
    }

    // Changes the contents of the game board at a specified row and column.
    override fun playToken(column: Int, player: Int): Boolean {
        /** :param column: The column of the most-recent token.
         *  :param player: The player of the token.
         *  :return: True if legal move, false otherwise.
         *  Alternates the player turns, iterates through the rows of
         *  the selected column, sets the first unassigned space to the player number.
         */
        if (player <= 0) {
            throw IllegalArgumentException("Player numbers start with 1.")
        }
        // Alternate player turns.
        playerTurn = if (playerTurn == 1) { 2 } else 1

        for (row in mRows-1 downTo 0) {
            if (mData[column][row] == 0) {
                mData[column][row] = player
                fireGameChange()
                return true
            }
        }
        fireGameChange()
        return false // Illegal move.
    }

    fun hasWon(column: Int, player: Int): Boolean {
        /** :param column: The column of the most-recent token.
         *  :param player: The player of the token.
         *  :return: True if win condition met, false otherwise.
         *  Checks for a continuous vertical line of 4.
         *  Checks for a continuous horizontal line of 4.
         */

        var currentRow = 0
        for(row in 0 until mRows) { // Iterate top-down.
            if (mData[column][row] == player) {
                break
            }
                currentRow++
        }

        // Column of 4.
        var count = 0 // Store how many in a row.
        for (row in mRows-1 downTo 0) {
            if(mData[column][row] == player) {
                count++
            }
            else { // Reset the count.
                count = 0
            }
            if(count == 4) return true
        }

        // Row of 4.
        count = 0
        for(col in mColumns-1 downTo 0) {
            if(mData[col][currentRow] == player) {
                count++
            }
            else {
                count = 0
            }
            if(count == 4) return true
        }


        // Diagonal of 4.

        // Top-left to bottom-right diagonal.
        count = 1 // Start count with the token just played.
        for(i in 1..3) {
            try {
                // Go diagonally towards top-left until enemy token.
                if (mData[column - i][currentRow - i] == player)
                    count++

                if (count == 4) return true
            }
            catch(ex: ArrayIndexOutOfBoundsException) {
                break
            }
        }
        for(i in 1..3) {
            try {
                // Go diagonally towards bottom-right until enemy token.
                if (mData[column + i][currentRow + i] == player)
                    count++

                if (count == 4) return true
            }
            catch(ex: ArrayIndexOutOfBoundsException) {
                break
            }
        }

        // Top-right to bottom-left diagonal.
        count = 1
        for(i in 1..3) {
            try {
                // Go diagonally towards top-right until enemy token.
                if (mData[column + i][currentRow - 1] == player)
                    count++

                if (count == 4) return true
            }
            catch(ex: ArrayIndexOutOfBoundsException) {
                break
            }
        }
        for(i in 1..3) {
            try {
                // Go diagonally towards bottom-left until enemy token.
                if (mData[column - i][currentRow + i] == player)
                    count++

                if (count == 4) return true
            }
            catch(ex: ArrayIndexOutOfBoundsException) {
                break
            }
        }
        return false
    }
}

