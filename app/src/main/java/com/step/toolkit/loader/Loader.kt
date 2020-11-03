package com.step.toolkit.loader

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.step.gankall.R
import com.step.toolkit.util.size.DimenUtil
import timber.log.Timber

object Loader {
    private const val LOADER_SIZE_SCALE = 8
    private const val LOADER_OFFSET_SCALE = 10
    private val LOADERS: ArrayList<AppCompatDialog> =
        ArrayList()

    fun showLoading(context: Context?) {

        var appCompatDialog = AppCompatDialog(context, R.style.LoadingDialog2)
        appCompatDialog.setContentView(TextView(context))


        val deviceWidth = DimenUtil.getScreenWidth()
        val deviceHeight = DimenUtil.getScreenHeight()
        appCompatDialog.window?.apply {
            with(attributes) {
                width = deviceWidth / LOADER_SIZE_SCALE
                height = deviceHeight / LOADER_SIZE_SCALE
                height += deviceHeight / LOADER_OFFSET_SCALE
                gravity = Gravity.CENTER

                Timber.d("${deviceWidth / LOADER_SIZE_SCALE}")
                Timber.d("${deviceHeight / LOADER_SIZE_SCALE}")
                Timber.d("${deviceWidth / LOADER_SIZE_SCALE}")
            }
        }
        LOADERS.add(appCompatDialog)
        appCompatDialog.show()
    }

    fun stopLoading() {
        LOADERS.forEach { dialog ->
            dialog.apply {
                if (dialog.isShowing) {
                    dialog.cancel()
                }
            }

        }
    }

}