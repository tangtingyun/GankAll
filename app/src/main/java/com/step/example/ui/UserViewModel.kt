package com.step.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.step.example.data.repository.UserRepo
import com.step.example.data.state.Resource
import timber.log.Timber

class UserViewModel(
    private val userRepo: UserRepo
) : ViewModel() {

    fun getUsers() = liveData {
        Timber.d("${Thread.currentThread()}")
        emit(Resource.loading(data = null))
        try {
            var data = userRepo.getUsers()
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}