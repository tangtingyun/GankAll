package com.step.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.step.data.repository.UserRepo
import com.step.data.state.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.RuntimeException

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