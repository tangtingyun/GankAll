package com.step.example.ui.base

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.step.example.data.api.ApiService
import com.step.example.data.api.RetrofitBuilder
import com.step.example.data.repository.UserRepo
import com.step.example.ui.BannerViewModel
import com.step.example.ui.UserViewModel


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
            modelClass.isAssignableFrom(BannerViewModel::class.java) -> {
                BannerViewModel(UserRepo(apiService))
            }
            else -> {
                throw IllegalArgumentException("Unknow class name ${modelClass}")
            }
        } as T
    }
}