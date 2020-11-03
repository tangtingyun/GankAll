package com.step.data.repository

import com.step.data.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DownloadRepo(private val apiService: ApiService) {

    suspend fun downloadUsers() = withContext(Dispatchers.IO) {
        Timber.d("${Thread.currentThread()}")



/*
        apiService.downloadUsers("").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var progressResponseBody =
                        ProgressResponseBody(responseBody = response.body()!!) { bytesRead, contentLength, done ->
                            Timber.d("$bytesRead,  $contentLength,  $done")
                        }
                    var string = progressResponseBody.string()
                    Timber.d("$string")
                }
            }
        })
*/
    }


}