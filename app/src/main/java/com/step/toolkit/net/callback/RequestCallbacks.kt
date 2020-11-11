package com.step.toolkit.net.callback

import android.os.Handler
import com.step.toolkit.loader.Loader
import com.step.toolkit.global.AppConfig
import com.step.toolkit.global.GlobalKeys

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RequestCallbacks(
    private val success: ISuccess?,
    private val error: IError?,
    private val showLoading: Boolean
) : Callback<String> {

    override fun onResponse(call: Call<String>, response: Response<String>) {
        if (response.isSuccessful) {
            if (call.isExecuted) {
                if (success != null) {
                    if (response.body() != null) {
                        try {
                            success.onSuccess(response.body()!!)
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            error?.onError(response.code(), response.message())
                        }
                    }
                }
            }
        } else {
            error?.onError(response.code(), response.message())
        }

        onRequestFinish()
    }

    private fun onRequestFinish() {
        if (showLoading) {
            val delayed = AppConfig.getConfiguration<Long>(GlobalKeys.LOADER_DELAYED)
            HANDLER.postDelayed({ Loader.stopLoading() }, delayed)
        }
    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        error?.onError(0, t.message ?: t.localizedMessage)
    }

    companion object {
        private val HANDLER =
            AppConfig.getConfiguration<Handler>(GlobalKeys.HANDLER)
    }


}