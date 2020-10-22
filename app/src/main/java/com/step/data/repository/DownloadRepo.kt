package com.step.data.repository

import com.step.data.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class DownloadRepo(private val apiService: ApiService) {

    suspend fun downloadUsers() = withContext(Dispatchers.IO) {
        Timber.d("${Thread.currentThread()}")
        apiService.downloadUsers()
    }


}