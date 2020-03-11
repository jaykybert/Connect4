package com.example.connect4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

import com.example.logic.StudentGame

class GameView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mStudentGame: StudentGame = StudentGame(8, 12)
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

            // Refresh the screen display (this is instead of using Listeners).
            invalidate()
            return true
        }
    } // End of MyGestureListener class.

    companion object { // Declare a constant (must be in the companion.
        const val LOGTAG = "MyTask"
    }
}