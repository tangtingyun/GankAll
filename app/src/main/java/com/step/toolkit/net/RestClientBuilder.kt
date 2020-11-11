package com.step.toolkit.net

import android.content.Context
import com.step.toolkit.net.callback.*
import java.util.*

/**
 * 构建RestClient并初始化参数和回调
 */
class RestClientBuilder(
    private var url: String? = null,
    private var success: ISuccess? = null,
    private var error: IError? = null,
    private var context: Context? = null,
    private var showLoading: Boolean = true
) {

    private val mParams = WeakHashMap<String, Any>()
    private val mHeaders = WeakHashMap<String, Any>()

    fun url(url: String): RestClientBuilder {
        this.url = url
        return this
    }

    fun params(key: String, value: Any): RestClientBuilder {
        mParams[key] = value
        return this
    }

    fun params(params: WeakHashMap<String, Any>): RestClientBuilder {
        mParams.putAll(params)
        return this
    }

    fun headers(key: String, value: Any): RestClientBuilder {
        mHeaders[key] = value
        return this
    }

    fun headers(params: WeakHashMap<String, Any>): RestClientBuilder {
        mHeaders.putAll(params)
        return this
    }

    fun success(iSuccess: ISuccess): RestClientBuilder {
        this.success = iSuccess
        return this
    }

    fun error(iError: IError): RestClientBuilder {
        this.error = iError
        return this
    }

    fun loader(context: Context, showLoading: Boolean): RestClientBuilder {
        this.context = context
        this.showLoading = showLoading
        return this
    }

    fun loader(context: Context): RestClientBuilder {
        this.context = context
        this.showLoading = true
        return this
    }

    fun build(): RestClient {
        return RestClient(
            url,
            mParams,
            mHeaders,
            success,
            error,
            context!!, showLoading
        )
    }

}












