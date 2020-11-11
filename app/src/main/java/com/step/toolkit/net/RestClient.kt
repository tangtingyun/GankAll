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
    private val headers: WeakHashMap<String, Any>?,
    private val success: ISuccess?,
    private val error: IError?,
    private val context: Context,
    private val showLoading: Boolean
) {

    companion object {
        fun builder(): RestClientBuilder {
            return RestClientBuilder()
        }
    }

    private fun request(method: HttpMethod) {
        val service = RestCreator.restService
        val call: Call<String>?

        if (showLoading) {
            Loader.showLoading(context);
        }
        call = when (method) {

            HttpMethod.GET -> service.get(url, params, headers)
            HttpMethod.POST -> service.post(url, params, headers)
            HttpMethod.PUT -> service.put(url, params, headers)
            HttpMethod.DELETE -> service.delete(url, params, headers)
            //以下先不实现
            HttpMethod.UPLOAD -> TODO()
            HttpMethod.DOWNLOAD -> TODO()
        }

        call.enqueue(requestCallback)
    }

    private val requestCallback: Callback<String>
        get() = RequestCallbacks(success, error, showLoading)

    fun get() {
        request(HttpMethod.GET)
    }

    fun post() {
        request(HttpMethod.POST)
    }

    fun put() {
        request(HttpMethod.PUT)
    }

    fun delete() {
        request(HttpMethod.DELETE)
    }
}