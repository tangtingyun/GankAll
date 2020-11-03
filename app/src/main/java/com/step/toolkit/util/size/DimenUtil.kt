package com.step.toolkit.util.size

import com.step.toolkit.global.AppGlobals

object DimenUtil {
    fun getScreenWidth(): Int {
        val dm = AppGlobals.APP.resources.displayMetrics
        return dm.widthPixels
    }

    fun getScreenHeight(): Int {
        val dm = AppGlobals.APP.resources.displayMetrics
        return dm.heightPixels
    }
}