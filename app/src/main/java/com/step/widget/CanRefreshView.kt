package com.step.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Scroller
import androidx.core.view.ViewCompat
import timber.log.Timber
import kotlin.math.abs


class CanRefreshView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    lateinit var mTarget // the target of the gesture
            : View

    lateinit var mHeaderView: ImageView
    private var mHeaderViewIndex = -1
    private val MIN_FLING_VELOCITY = 400 // dips
    private var mTouchSlop = 0
    private var mMinimumVelocity = 0
    private var mMaximumVelocity = 0


    /**
     * Sentinel value for no current active pointer.
     * Used by [.mActivePointerId].
     */
    private val INVALID_POINTER = -1

    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private var mActivePointerId = INVALID_POINTER


    val HEADER_DIAMETER = 200

    // Default offset in dips from the top of the view to where the progress spinner should stop
    val DEFAULT_HEADER_TARGET = 64

    var mTotalDragDistance = -1f

    private var mHeaderDiameter = 0

    var mSpinnerOffsetEnd = 0

    var mCustomSlingshotDistance = 0

    private var mOriginalOffsetTop = 0
    private var mCurrentTargetOffsetTop = 0

    private var mInitialMotionY = 0f
    private var mInitialDownY = 0f
    private var mIsBeingDragged = false
    private var mIsUnableToDrag = false

    private var mVelocityTracker: VelocityTracker? = null

    lateinit var mScroller: Scroller
    private var mIsScrollStarted = false

    init {
        val configuration = ViewConfiguration.get(context)

        val density = context.resources.displayMetrics.density
        val metrics = resources.displayMetrics

        mHeaderDiameter = (HEADER_DIAMETER * density).toInt()

        mTouchSlop = configuration.scaledPagingTouchSlop

        mMinimumVelocity = (MIN_FLING_VELOCITY * density).toInt()
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity



        setWillNotDraw(false)
        isChildrenDrawingOrderEnabled = true

        // the absolute offset has to take into account that the circle starts at an offset
        mSpinnerOffsetEnd = (DEFAULT_HEADER_TARGET * metrics.density).toInt()
        mTotalDragDistance = mSpinnerOffsetEnd.toFloat()

        mCurrentTargetOffsetTop = -mHeaderDiameter;
        mOriginalOffsetTop = -mHeaderDiameter;

        mScroller = Scroller(getContext())
        createHeaderView()
    }

    private fun createHeaderView() {
        mHeaderView = ImageView(context)
//        mHeaderView.visibility = View.GONE
        mHeaderView.setBackgroundColor(Color.RED)
        addView(mHeaderView)
    }

    private fun ensureTarget() {
        if (!this::mTarget.isInitialized) {
            for (i in 0 until childCount) {
                var child = getChildAt(i)
                if (!child.equals(mHeaderView)) {
                    mTarget = child;
                    break;
                }
            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!this::mTarget.isInitialized) {
            ensureTarget()
        }
        if (!this::mTarget.isInitialized) {
            return;
        }
        mTarget.measure(
            MeasureSpec.makeMeasureSpec(
                measuredWidth - paddingLeft - paddingRight,
                MeasureSpec.EXACTLY
            ), MeasureSpec.makeMeasureSpec(
                measuredHeight - paddingTop - paddingBottom,
                MeasureSpec.EXACTLY
            )
        )

        mHeaderView.measure(
            MeasureSpec.makeMeasureSpec(
                measuredWidth - paddingLeft - paddingRight,
                MeasureSpec.EXACTLY
            ), MeasureSpec.makeMeasureSpec(
                mHeaderDiameter,
                MeasureSpec.EXACTLY
            )
        )

        mHeaderViewIndex = -1;
        for (index in 0 until childCount) {
            var child = getChildAt(index)
            if (child.equals(mHeaderView)) {
                mHeaderViewIndex = index;
                break;
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        val width = measuredWidth;
        val height = measuredHeight;


        if (childCount == 0) return;
        if (!this::mTarget.isInitialized) {
            ensureTarget();
        }

        if (!this::mTarget.isInitialized) {
            return;
        }

        val child = mTarget;

        val childLeft = paddingLeft;
        val childTop = paddingTop;
        val childWidth = width - paddingLeft - paddingRight;
        val childHeight = height - paddingTop - paddingBottom;
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

        var headerWidth = mHeaderView.measuredWidth;
        var headerHeight = mHeaderView.measuredHeight;

        mHeaderView.layout(
            childLeft,
            mCurrentTargetOffsetTop,
            childLeft + headerWidth,
            mCurrentTargetOffsetTop + headerHeight
        );
    }


    override fun getChildDrawingOrder(childCount: Int, drawingPosition: Int): Int {
        if (mHeaderViewIndex < 0) {
            return drawingPosition;
        } else if (drawingPosition == childCount - 1) {
            return mHeaderViewIndex;
        } else if (drawingPosition >= mHeaderViewIndex) {
            return drawingPosition + 1;
        } else {
            return drawingPosition;
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.apply {
            when (actionMasked) {
                MotionEvent.ACTION_DOWN -> {
//                    setTargetOffsetTopAndBottom(mOriginalOffsetTop - mHeaderView.getTop())
                    mIsBeingDragged = false
                    mInitialDownY = y;
                }
                MotionEvent.ACTION_MOVE -> {
                    val yDiff: Float = abs(y - mInitialDownY)
                    if (yDiff > mTouchSlop && !mIsBeingDragged) {
                        mInitialMotionY = mInitialDownY + mTouchSlop;
                        mIsBeingDragged = true
                    }
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    mIsBeingDragged = false
                }
            }
        }
        return mIsBeingDragged
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    mIsBeingDragged = false
                }
                MotionEvent.ACTION_MOVE -> {
                    val yDiff: Float = abs(y - mInitialMotionY)
                    if (yDiff > mTouchSlop && !mIsBeingDragged) {
                        mInitialMotionY = mInitialDownY + mTouchSlop;
                        mIsBeingDragged = true
                    }
                    if (mIsBeingDragged) {
                        val overscrollTop =
                            (y - mInitialMotionY) * .5f
                        moveSpinner(overscrollTop)
                        if (overscrollTop > 0) {
//                            moveSpinner(overscrollTop)
                        } else {
//                            return false
                        }
                    }
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    if (mIsBeingDragged) {
                        val overscrollTop =
                            (y - mInitialMotionY) * .5f
                        mIsBeingDragged = false
                        finishSpinner(overscrollTop)
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun finishSpinner(overscrollTop: Float) {
        setTargetOffsetTopAndBottom(-mHeaderDiameter - mCurrentTargetOffsetTop)
    }

    private fun moveSpinner(overscrollTop: Float) {


        val originalDragPercent = overscrollTop / mTotalDragDistance

        val dragPercent =
            Math.min(1f, Math.abs(originalDragPercent))
        val adjustedPercent =
            Math.max(dragPercent - .4, 0.0).toFloat() * 5 / 3
        val extraOS = Math.abs(overscrollTop) - mTotalDragDistance
        val slingshotDist = mSpinnerOffsetEnd.toFloat()
        val tensionSlingshotPercent = Math.max(
            0f, Math.min(extraOS, slingshotDist * 2)
                    / slingshotDist
        )
        val tensionPercent = (tensionSlingshotPercent / 4 - Math.pow(
            (tensionSlingshotPercent / 4).toDouble(), 2.0
        )).toFloat() * 2f
        val extraMove = slingshotDist * tensionPercent * 2

        val targetY = mOriginalOffsetTop + (slingshotDist * dragPercent + extraMove).toInt()
        // where 1.0f is a full circle
        // where 1.0f is a full circle
        if (mHeaderView.getVisibility() != View.VISIBLE) {
            mHeaderView.setVisibility(View.VISIBLE)
        }

        if (overscrollTop < mTotalDragDistance) {

        } else {

        }
        val strokeStart = adjustedPercent * .8f

        setTargetOffsetTopAndBottom(targetY - mCurrentTargetOffsetTop)
    }

    fun setTargetOffsetTopAndBottom(offset: Int) {
        Timber.d("ACTION_MOVE : offset    ${offset}")


//        scrollBy(0, -offset.toInt())
//        mCurrentTargetOffsetTop = mHeaderView.getTop()

        mHeaderView.bringToFront()
        ViewCompat.offsetTopAndBottom(mHeaderView, offset)
        mCurrentTargetOffsetTop = mHeaderView.getTop()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}