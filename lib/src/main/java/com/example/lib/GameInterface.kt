package com.example.lib

interface GameInterface {
    val mColumns: Int
    val mRows: Int

    // Internal representation of the state of the game - the grid of coloured tokens.
    var mData: Array<IntArray>

    // Returns the state of the game grid at a specified column and row number.
    fun getToken(column: Int, row: Int): Int

    // Changes the contents of the game grid at a specified row and column.
    fun playToken(column: Int, player: Int): Boolean
}
