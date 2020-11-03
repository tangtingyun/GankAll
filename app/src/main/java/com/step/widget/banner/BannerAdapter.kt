package com.step.widget.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.postOnAnimationDelayed
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.step.data.model.Data
import com.step.gankall.R
import timber.log.Timber

class BannerAdapter : PagerAdapter() {

    private val mBannerBeanList: MutableList<Data> = arrayListOf()
    lateinit var mLoadShowAnimation: Animation
    lateinit var mLoadHideAnimation: Animation
    fun setBeanData(bannerList: List<Data>) {
        mBannerBeanList.clear()
        mBannerBeanList.addAll(bannerList)
        notifyDataSetChanged()
    }

    private lateinit var layoutInflater: LayoutInflater

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (!this::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(container.context)
        }

        if (!this::mLoadShowAnimation.isInitialized) {
            mLoadShowAnimation =
                AnimationUtils.loadAnimation(container.context, R.anim.banner_title_show)
        }

        if (!this::mLoadHideAnimation.isInitialized) {
            mLoadHideAnimation =
                AnimationUtils.loadAnimation(container.context, R.anim.banner_title_hide)
        }


        var itemView = layoutInflater.inflate(R.layout.banner_item_layout, container, false)

        if (itemView.parent != null && itemView.parent is ViewGroup) {
            (itemView.parent as ViewGroup).removeView(itemView)
        }

        var tvTitle = itemView.findViewById<TextView>(R.id.tv_banner_sutitle)
        var ivBanner = itemView.findViewById<ImageView>(R.id.iv_banner_view)

        tvTitle.text = mBannerBeanList[position].title

        Glide.with(ivBanner).load(mBannerBeanList[position].image)
            .centerCrop()
            .into(ivBanner)

        tvTitle.visibility = View.INVISIBLE
        tvTitle.tag = false

        var hideRunnable = {
            tvTitle.tag = false
            tvTitle.startAnimation(mLoadHideAnimation)
            tvTitle.visibility = View.INVISIBLE
            Timber.d(" 还是执行了?? ")
        }
        ivBanner.setOnClickListener { _ ->
            var removeCallbacks = tvTitle.removeCallbacks(hideRunnable)
            Timber.d("removeCallbacks  $removeCallbacks")
            tvTitle.postOnAnimationDelayed(hideRunnable, 3000)

            if (tvTitle.tag as Boolean) {
                return@setOnClickListener
            }
            tvTitle.tag = true
            tvTitle.visibility = View.VISIBLE
            tvTitle.startAnimation(mLoadShowAnimation)

        }
        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


    /*
    *
    * 注意这里  如果返回false  就不会显示
    * */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = mBannerBeanList.size
}