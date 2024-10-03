package com.will.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2

class BoxDrawingView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {

    private var currentBox: Box? = null
    private val boxen = mutableListOf<Box>()

    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt()
    }

    private val backgroundPoint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

    private var mode = Mode.DRAW
    private var oldAngle = 0f

    enum class Mode {
        DRAW, ROTATE
    }

    // 需要给 view 设置 id 否则，这两个方法不会回调执行
    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("super", super.onSaveInstanceState())
        bundle.putParcelableArrayList("boxen", ArrayList(boxen));
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = (state as Bundle)
        super.onRestoreInstanceState(bundle.getParcelable("super"))
        val restoredBoxen = state.getParcelableArrayList<Box>("boxen")
        if (restoredBoxen != null) {
            boxen.clear()
            boxen.addAll(restoredBoxen)
            invalidate()
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画背景
        canvas.drawPaint(backgroundPoint)
        boxen.forEach{box->
            canvas.save()
            canvas.rotate(box.rotation, box.centerX, box.centerY)
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
            canvas.restore()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mode = Mode.DRAW
                action = "ACTION_DOWN"
                currentBox = Box(current).also {
                    boxen.add(it)
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (event.pointerCount == 2) {
                    mode = Mode.ROTATE
                    oldAngle = rotation(event)
                }
                Log.e("WillWolf", "point down")
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                if (mode == Mode.DRAW) {
                    updateCurrentBox(current)
                } else if (mode == Mode.ROTATE) {
                    val newAngle = rotation(event)
                    val deltaAngle = newAngle - oldAngle
                    currentBox?.rotation = (currentBox?.rotation ?: 0f) + deltaAngle
                    oldAngle = newAngle
                    Log.e("WillWolf", "rotation-->" + rotation)
                }
                Log.e("WillWolf", "mode-->" + mode)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode = Mode.DRAW
            }

            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }
//        Log.e("WillWolf", "$action at x=${current.x}, y=${current.y}")
        invalidate()
        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            // 这会强制 BoxDrawingView 重新绘制自己
//            invalidate()
        }
    }

    private fun rotation(event: MotionEvent): Float {
        val deltaX = event.getX(0) - event.getX(1)
        val deltaY = event.getY(0) - event.getY(1)
        val radians = atan2(deltaY.toDouble(), deltaX.toDouble())
        return Math.toDegrees(radians).toFloat()
    }
}