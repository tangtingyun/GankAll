package com.step.example.widget.banner

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.step.example.data.model.Data

class Banner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewPager(context, attrs) {
    lateinit var mBannerAdapter: BannerAdapter

    init {

    }

    fun setUpData(list: List<Data>) {
        if (!this::mBannerAdapter.isInitialized) {
            mBannerAdapter = BannerAdapter()
            adapter = mBannerAdapter
        }
        mBannerAdapter.setBeanData(list)
    }
}