package com.step.data.repository

import com.step.data.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepo(private val apiService: ApiService) {

    suspend fun getUsers() = withContext(Dispatchers.IO) {
        Timber.d("${Thread.currentThread()}")
        apiService.getUsers()
    }

    suspend fun postUsers() = withContext(Dispatchers.IO) {
        Timber.d("${Thread.currentThread()}")
        apiService.postUsers()
    }
}