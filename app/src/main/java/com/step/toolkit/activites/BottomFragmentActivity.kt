package com.step.toolkit.activites

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.step.gankall.R
import com.step.toolkit.fragments.bottom.BottomItemFragment
import com.step.toolkit.fragments.bottom.BottomTabBean
import com.step.toolkit.fragments.bottom.ItemBuilder

abstract class BottomFragmentActivity : AppCompatActivity(),
    View.OnClickListener {

    private val mTabBean = ArrayList<BottomTabBean>()
    val mItemFragments = ArrayList<BottomItemFragment>()

    private val mItems = LinkedHashMap<BottomTabBean, BottomItemFragment>()

    private var mCurrentFragment = 0;

    // 设置首页一打开展示哪个fragment
    private var mIndexFragment = 0;


    private lateinit var mBottomBar: LinearLayoutCompat


    abstract fun setItems(builder: ItemBuilder)
            : LinkedHashMap<BottomTabBean, BottomItemFragment>


    //设置首页一打开展示哪个平级的Fragment
    abstract fun setIndexFragment(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    private fun initContainer(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
        }
        setContentView(R.layout.activity_bottom_fragment)
        mIndexFragment = setIndexFragment()


        val builder = ItemBuilder.builder()
        val items = setItems(builder)
        mItems.putAll(items)

        for ((key, value) in mItems) {
            mTabBean.add(key)
            mItemFragments.add(value)
        }

        mBottomBar = findViewById(R.id.bottom_bar)
        val size = mItems.size
        val layoutInflater = LayoutInflater.from(this)
        for (i in 0 until size) {
            layoutInflater.inflate(
                R.layout.bottom_item_icon_text,
                mBottomBar
            )
            val item = mBottomBar.getChildAt(i) as RelativeLayout

            item.tag = i
            item.setOnClickListener(this)
            val itemIcon = item.getChildAt(0) as AppCompatImageView
            val itemTitle = item.getChildAt(1) as AppCompatTextView
            val bean = mTabBean[i]
            itemIcon.setImageResource(bean.normalIcon)
            itemTitle.setTextColor(bean.normalTextColor)

            itemTitle.setText(bean.title)
            if (i == mIndexFragment) {
                itemIcon.setImageResource(bean.selectIcon)
                itemTitle.setTextColor(bean.selectTextColor)
            }
        }
    }

    private fun resetColor() {
        val count = mBottomBar.childCount
        for (i in 0 until count) {
            val item =
                mBottomBar.getChildAt(i) as RelativeLayout
            val itemIcon =
                item.getChildAt(0) as AppCompatImageView
            val itemTitle =
                item.getChildAt(1) as AppCompatTextView
            val bean = mTabBean[i]
            itemIcon.setImageResource(bean.normalIcon)
            itemTitle.setTextColor(bean.normalTextColor)
        }
    }

    private fun changeColor(tabIndex: Int) {
        resetColor()
        val item =
            mBottomBar.getChildAt(tabIndex) as RelativeLayout
        val itemIcon =
            item.getChildAt(0) as AppCompatImageView
        val itemTitle =
            item.getChildAt(1) as AppCompatTextView

        val bean = mTabBean[tabIndex]
        itemIcon.setImageResource(bean.selectIcon)
        itemTitle.setTextColor(bean.selectTextColor)
    }

    override fun onClick(v: View) {
        val tabIndex = v.tag as Int
        changeColor(tabIndex)
        //展示和隐藏内容部分的

        //先后顺序不能错
        mCurrentFragment = tabIndex
    }
}