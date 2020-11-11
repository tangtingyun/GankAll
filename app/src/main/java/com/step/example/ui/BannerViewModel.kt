package com.step.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.step.example.data.repository.UserRepo
import com.step.example.data.state.Resource
import timber.log.Timber

class BannerViewModel(
    private val userRepo: UserRepo
) : ViewModel() {

    fun getBanner() = liveData {
        Timber.d("${Thread.currentThread()}")
        emit(Resource.loading(data = null))
        try {
//            throw RuntimeException("oops!! ")
            var data = userRepo.getBanner()
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}