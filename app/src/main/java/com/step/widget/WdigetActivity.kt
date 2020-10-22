package com.step.widget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.observe
import com.step.data.state.Status
import com.step.gankall.R
import com.step.ui.UserViewModel
import com.step.ui.base.viewModelsByApp
import timber.log.Timber

class WdigetActivity : AppCompatActivity() {
    val userViewModel: UserViewModel by viewModelsByApp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate   后执行了")
        setContentView(R.layout.activity_wdiget)
        testResourses()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Timber.d("$userViewModel")
        userViewModel.getUsers().observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Timber.d("Status.SUCCESS:  ${resource.data}")
                }
                Status.ERROR -> {
                    Timber.d("Status.ERROR:  ${resource.message}")
                }
                Status.LOADING -> {
                    Timber.d("Status.LOADING")
                }
            }
        }

    }

    private fun testResourses() {
        var resourceEntryName = resources.getResourceEntryName(R.string.app_name)
        var resourceTypeName = resources.getResourceTypeName(R.string.app_name)
        var identifier = resources.getIdentifier(resourceEntryName, resourceTypeName, packageName)

        Timber.d(
            "\n resourceEntryName    $resourceEntryName, \n" +
                    "resourceTypeName   $resourceTypeName,  \n" +
                    "identifier         $identifier"
        )
    }
}
