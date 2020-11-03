package com.step.toolkit.net

import android.content.Context
import com.step.toolkit.loader.Loader
import com.step.toolkit.net.callback.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*

/**
 * 对外暴露直接使用的客户端
 */
class RestClient internal constructor(
    private val url: String?,
    private val params: WeakHashMap<String, Any>?,
    private val request: IRequest?,
    private val success: ISuccess?,
    private val failure: IFailure?,
    private val error: IError?,
    private val complete: IComplete?,
    private val context: Context?,
    private val showLoading: Boolean
) {

    companion object {
        fun builder(): RestClientBuilder {
            return RestClientBuilder()
        }
    }

    private suspend fun request(method: HttpMethod) {
        val service = RestCreator.restService
        val call: Call<String>?
        request?.onRequestStart()

        if (showLoading) {
            Loader.showLoading(context);
        }
        call = when (method) {

            HttpMethod.GET -> service.get(url, params)
            HttpMethod.POST -> service.post(url, params)
            HttpMethod.PUT -> service.put(url, params)
            HttpMethod.DELETE -> service.delete(url, params)
            //以下先不实现
            HttpMethod.UPLOAD -> TODO()
            HttpMethod.DOWNLOAD -> TODO()
        }

        call.enqueue(requestCallback)
    }

    private val requestCallback: Callback<String>
        get() = RequestCallbacks(request, success, failure, error, complete, showLoading)

    suspend fun get() {
        request(HttpMethod.GET)
    }

    suspend fun post() {
        request(HttpMethod.POST)
    }

    suspend fun put() {
        request(HttpMethod.PUT)
    }

    suspend fun delete() {
        request(HttpMethod.DELETE)
    }
}