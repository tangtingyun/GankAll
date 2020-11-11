package com.step.toolkit.loader

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.step.gankall.R
import com.step.toolkit.util.size.PixUtils.dp2px
import com.step.toolkit.util.view.ViewHelper
import com.step.toolkit.util.view.ViewHelper.setViewOutline

class LoadingDialog : AlertDialog {
    private var loadingText: TextView? = null

    constructor(context: Context) : super(context) {}

    fun setLoadingText(loadingText: String?) {
        if (this.loadingText != null) {
            this.loadingText!!.text = loadingText
        }
    }

    override fun show() {
        super.show()
        setContentView(R.layout.layout_loading_view)
        loadingText = findViewById(R.id.loading_text)
        val window = window
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.WRAP_CONTENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
        attributes.gravity = Gravity.CENTER
        attributes.dimAmount = 0.5f
        //这个背景必须设置哦，否则 会出现对话框 宽度很宽
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //给转菊花的loading对话框来一个圆角
        setViewOutline(
            findViewById(R.id.loading_layout)!!,
            dp2px(10),
            ViewHelper.RADIUS_ALL
        )
        window.attributes = attributes
    }
}