package com.step.toolkit.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserManager private constructor() {
    private val userLiveData = MutableLiveData<User>()

    private var mUser: User? = null

    fun save(user: User) {
        mUser = user
        if (userLiveData.hasObservers()) {
            userLiveData.postValue(user)
        }
    }

    fun login(context: Context?): LiveData<User> {
//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
        return userLiveData
    }

    val isLogin: Boolean
        get() = false

    val user: User?
        get() = if (isLogin) mUser else null

    fun logout() {
        mUser = null
    }

    companion object {
        private val mUserManager =
            UserManager()

        fun get(): UserManager {
            return mUserManager
        }
    }
}