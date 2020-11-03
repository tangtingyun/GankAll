package com.step.data.api

import com.step.data.model.BannerBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {

    @GET("banners")
    suspend fun getBanner(): BannerBean

    @GET("banners")
    suspend fun getUsers(): String

    @GET("users")
    suspend fun postUsers(): String

    @GET
    suspend fun downloadUsers(@Url url: String): ResponseBody

    @GET
    fun downloadUsers2(@Url url: String): Call<ResponseBody>

    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): Call<ResponseBody>

    @GET
    operator fun get(
        @Url url: String,
        @QueryMap params: WeakHashMap<String, Any>
    ): Call<String>


}