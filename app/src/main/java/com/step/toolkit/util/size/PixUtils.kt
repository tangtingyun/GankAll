package com.step.toolkit.util.size

import android.util.DisplayMetrics
import com.step.toolkit.global.AppGlobals

object PixUtils {
    fun getScreenWidth(): Int {
        val dm = AppGlobals.APP.resources.displayMetrics
        return dm.widthPixels
    }

    fun getScreenHeight(): Int {
        val dm = AppGlobals.APP.resources.displayMetrics
        return dm.heightPixels
    }


    fun dp2px(dpValue: Int): Int {
        val metrics: DisplayMetrics = AppGlobals.APP.resources.displayMetrics
        return (metrics.density * dpValue + 0.5f).toInt()
    }
}