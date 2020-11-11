package com.step.example.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

typealias OnPreInterceptTouchEventDelegate = (ev: MotionEvent?) -> Boolean

class AdvanceSwipeRefreshLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    private var mOnPreInterceptTouchEventDelegate: OnPreInterceptTouchEventDelegate? = null

    fun setOnPreInterceptTouchEventDelegate(listener: OnPreInterceptTouchEventDelegate) {
        mOnPreInterceptTouchEventDelegate = listener
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var disallowIntercept = mOnPreInterceptTouchEventDelegate?.let { it(ev) } ?: false
        return if (disallowIntercept) {
            false
        } else super.onInterceptTouchEvent(ev)
    }
}