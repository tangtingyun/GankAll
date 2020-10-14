package com.step.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup


class CanRefreshView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    var TAG = "CanRefreshView"

    private val MIN_FLING_VELOCITY = 400 // dips
    private var mTouchSlop = 0
    private var mMinimumVelocity = 0
    private var mMaximumVelocity = 0

    init {
        val configuration = ViewConfiguration.get(context)
        val density = context.resources.displayMetrics.density

        mTouchSlop = configuration.scaledPagingTouchSlop
        mMinimumVelocity = (MIN_FLING_VELOCITY * density).toInt()
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top = -(getChildAt(0).measuredHeight) / 2
        for (index in 0 until childCount) {
            Log.e(TAG, "$index --  ${getChildAt(index)} -- $top")
            var childView = getChildAt(index)
            childView.layout(0, top, childView.measuredWidth, top + childView.measuredHeight)
            top += childView.measuredHeight
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}