package com.step.ui.base

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.step.data.api.ApiService
import com.step.data.api.RetrofitBuilder
import com.step.data.repository.UserRepo
import com.step.ui.UserViewModel


inline fun <reified VM : ViewModel> ComponentActivity.viewModelsByApp(): Lazy<VM> = viewModels {
    AppViewModelFactory(
        RetrofitBuilder.apiService
    )
}


class AppViewModelFactory(
    private val apiService: ApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(UserRepo(apiService))
            }
            else -> {
                throw IllegalArgumentException("Unknow class name ${modelClass}")
            }
        } as T
    }
}