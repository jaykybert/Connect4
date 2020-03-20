package com.example.connect4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.logic.GameChangeInterface
import com.example.logic.StudentGame


class GameView(cols: Int, rows: Int, context: Context?): View(context) {

    private val numOfCols: Int = cols
    private val numOfRows: Int = rows
    private var mStudentGame: StudentGame

    /* listenerImp represents the implementation of a function, as specified in
 the GameChangeInterface interface, called onGameChange. */
    private var listenerImp = object: GameChangeInterface {
        override fun onGameChange(studentGame: StudentGame) {
            // Things that we want to do in this View when the game state changes.
            invalidate()
        }
    }

    init {
        mStudentGame = StudentGame(numOfCols, numOfRows)
        mStudentGame.setGameChangeListener(listenerImp)
    }

    private val colCount = mStudentGame.mColumns
    private val rowCount = mStudentGame.mRows

    private var mGridPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }
    private var mNoPlayerPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private var mPlayer1Paint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
    }
    private var mPlayer2Paint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
    }

    private val myGestureDetector = GestureDetector(context, MyGestureListener())


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val chosenDiameter: Float
        var tokenAtPos: Int
        var paint: Paint

        val viewWidth: Float = width.toFloat()
        val viewHeight: Float = height.toFloat()

        val diameterX: Float = viewWidth / colCount.toFloat()
        val diameterY: Float = viewHeight / rowCount.toFloat()

        // Choose the smallest of the two.
        chosenDiameter = if (diameterX < diameterY)
            diameterX
        else
            diameterY

        // Draw the game board.
        canvas.drawRect(0.toFloat(), 0.toFloat(), viewWidth, viewHeight, mGridPaint)

        val radius = chosenDiameter / 2
        // Draw the circles on the game board.
        for (col in 0 until colCount) {
            for (row in 0 until rowCount) {
                // Does the game array contain a piece at this location.
                tokenAtPos = mStudentGame.getToken(col, row)

                // Choose the correct colour for the circle.
                paint = when(tokenAtPos) {
                    1 -> mPlayer1Paint
                    2 -> mPlayer2Paint
                    else -> mNoPlayerPaint
                }



                // Calculate the co-ordinates of the circle.
                val cx = chosenDiameter * col + radius
                val cy = chosenDiameter * row + radius
                canvas.drawCircle(cx, cy, radius, paint)
            }
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return myGestureDetector.onTouchEvent(ev) || super.onTouchEvent(ev)
    }

    inner class MyGestureListener: GestureDetector.SimpleOnGestureListener() {
        // You should always include onDown() and it should always return true.
        // Otherwise the GestureListener may ignore other events.
        override fun onDown(ev: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(ev: MotionEvent): Boolean {
            val turn = mStudentGame.playerTurn

            // Work out the width of each column in pixels.
            val colWidth = width / colCount

            // Calculate the column number from the X co-ordinate of the touch event.
            val colTouch = ev.x.toInt() / colWidth

            // Tell the game logic that the user has chosen a column.
            mStudentGame.playToken(colTouch, turn)

            if(mStudentGame.hasWon(colTouch, turn)) {
                Toast.makeText(context, "Player $turn has won!", Toast.LENGTH_LONG).show()
            }
            return true
        }
    }
}