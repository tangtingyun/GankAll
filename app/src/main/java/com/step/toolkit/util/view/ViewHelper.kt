package com.step.toolkit.util.view

import android.annotation.TargetApi
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

object ViewHelper {
    const val RADIUS_ALL = 0
    const val RADIUS_LEFT = 1
    const val RADIUS_TOP = 2
    const val RADIUS_RIGHT = 3
    const val RADIUS_BOTTOM = 4

    fun setViewOutline(owner: View, radius: Int, radiusSide: Int) {
        owner.outlineProvider = object : ViewOutlineProvider() {
            @TargetApi(21)
            override fun getOutline(view: View, outline: Outline) {
                val w = view.width
                val h = view.height
                if (w == 0 || h == 0) {
                    return
                }
                if (radiusSide != RADIUS_ALL) {
                    var left = 0
                    var top = 0
                    var right = w
                    var bottom = h
                    if (radiusSide == RADIUS_LEFT) {
                        right += radius
                    } else if (radiusSide == RADIUS_TOP) {
                        bottom += radius
                    } else if (radiusSide == RADIUS_RIGHT) {
                        left -= radius
                    } else if (radiusSide == RADIUS_BOTTOM) {
                        top -= radius
                    }
                    outline.setRoundRect(left, top, right, bottom, radius.toFloat())
                    return
                }
                val top = 0
                val left = 0
                if (radius <= 0) {
                    outline.setRect(left, top, w, h)
                } else {
                    outline.setRoundRect(left, top, w, h, radius.toFloat())
                }
            }
        }
        owner.clipToOutline = radius > 0
        owner.invalidate()
    }
}