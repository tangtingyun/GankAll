package com.step.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.step.gankall.R
import com.step.toolkit.global.AppGlobals
import com.step.toolkit.loader.Loader
import com.step.example.ui.BannerViewModel
import com.step.example.ui.base.viewModelsByApp
import kotlinx.android.synthetic.main.activity_top_banner.*
import timber.log.Timber

class TopBannerActivity : AppCompatActivity() {
    private val mBannerViewModel: BannerViewModel by viewModelsByApp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_banner)

        Timber.d("$mBannerViewModel")
        mb_app.setOnClickListener {
            Timber.d("${AppGlobals.APP}")
            Loader.showLoading(this)
        }

        mb_app_stop.setOnClickListener {
            Loader.stopLoading()
        }
//        mBannerViewModel.getBanner().observe(this) { resource ->
//            when (resource.status) {
//                Status.SUCCESS -> {
//                    Timber.d("Status.SUCCESS:  ${resource.data}")
//                    resource.data?.apply {
//                        banner.setUpData(data)
//                    }
//                }
//                Status.ERROR -> {
//                    Timber.d("Status.ERROR:  ${resource.message}")
//                }
//                Status.LOADING -> {
//                    Timber.d("Status.LOADING")
//                }
//            }
//        }
    }
}
