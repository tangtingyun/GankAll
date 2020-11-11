package com.step.gankall

import com.step.example.data.api.RetrofitBuilder
import com.step.example.data.repository.ProgressResponseBody
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Test

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun mockProgress3() {
        // public abstract retrofit2.Call com.step.example.data.api.ApiService.downloadUsers2(java.lang.String)
        // (Ljava/lang/String;)Lretrofit2/Call<Lokhttp3/ResponseBody;>;
        var downloadUsers =
            RetrofitBuilder.apiService.downloadUsers2("https://gank.io/api/v2/banners")
        var response = downloadUsers.execute()
        println(response.isSuccessful)
    }

    @Test
    fun mockProgress2() {
        var countDownLatch = CountDownLatch(1)

        RetrofitBuilder.apiService.downloadUsers2("https://gank.io/api/v2/banners")
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    println(t)
                    countDownLatch.countDown()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        var progressResponseBody =
                            ProgressResponseBody(responseBody = response.body()!!) { bytesRead, contentLength, done ->
                                println("$bytesRead,  $contentLength,  $done")
                            }
                        var string = progressResponseBody.string()
                        println("$string")
                    }
                    countDownLatch.countDown()
                }
            })
        countDownLatch.await()
    }

    @Test
    fun mockProgress() {
        runBlocking {

            // public abstract retrofit2.Call com.step.example.data.api.ApiService.downloadUsers2(java.lang.String)
            // (Ljava/lang/String;)Lretrofit2/Call<Lokhttp3/ResponseBody;>;

            //  public abstract java.lang.Object com.step.example.data.api.ApiService.downloadUsers(java.lang.String,kotlin.coroutines.Continuation)
            //  (Ljava/lang/String;Lkotlin/coroutines/Continuation<-Lretrofit2/Call<Lokhttp3/ResponseBody;>;>;)Ljava/lang/Object;

            // http://file-jz.jingzhou.fuxing.palmyou.com/file/downloadFile?fileName=/jzapp/tomcat8070/templatefile/3117a12b43e7429c8d8bef270b61fe3a.txt
            // https://gank.io/api/v2/banners
            var response =
//                RetrofitBuilder.apiService.downloadUsers("http://file-jz.jingzhou.fuxing.palmyou.com/file/downloadFile?fileName=/jzapp/tomcat8070/templatefile/3117a12b43e7429c8d8bef270b61fe3a.txt")
                RetrofitBuilder.apiService.downloadUsers("https://gank.io/api/v2/banners")
            var progressResponseBody =
                ProgressResponseBody(responseBody = response) { bytesRead, contentLength, done ->
                    println("$bytesRead,  $contentLength,  $done")
                }
            var string = progressResponseBody.bytes()
            println("$string")
            println("${Thread.currentThread()}")
        }
        println("${Thread.currentThread()}")
    }

    @Test
    fun kotlinLaunch() {
        var launch = GlobalScope.launch {

        }
        var async = GlobalScope.async {

        }
    }
}
