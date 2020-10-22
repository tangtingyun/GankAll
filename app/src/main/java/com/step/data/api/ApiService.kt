package com.step.data.api

import com.step.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("banners")
    suspend fun getUsers(): String

    @GET("users")
    suspend fun postUsers(): String

    @GET("users")
    suspend fun downloadUsers(): String


}