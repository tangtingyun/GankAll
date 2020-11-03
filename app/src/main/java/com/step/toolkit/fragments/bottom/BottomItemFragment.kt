package com.step.toolkit.fragments.bottom

import android.widget.Toast
import com.step.toolkit.fragments.BaseFragment

abstract class BottomItemFragment : BaseFragment() {
    private var mTouchTime: Long = 0

    companion object {
        private const val WAIT_TIME = 2000L
    }

    fun onBackPressedSupport(): Boolean {
        activity?.apply {
            if (System.currentTimeMillis() - mTouchTime < WAIT_TIME) {
                finish()
            } else {
                mTouchTime = System.currentTimeMillis()
                Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

}