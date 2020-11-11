package com.step.toolkit.activites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.step.gankall.R
import com.step.toolkit.fragments.bottom.BottomItemFragment
import com.step.toolkit.fragments.bottom.BottomTabBean
import com.step.toolkit.fragments.bottom.ItemBuilder

abstract class BottomFragmentActivity : AppCompatActivity(),
    View.OnClickListener {

    private val mTabBean = ArrayList<BottomTabBean>()
    val mItemFragments = ArrayList<BottomItemFragment>()

    private val mItems = LinkedHashMap<BottomTabBean, BottomItemFragment>()

    // 设置当前展示的页面下标
    private var mCurrentFragment = -1;


    private lateinit var mBottomBar: LinearLayoutCompat

    private lateinit var mManager: FragmentManager
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
        mCurrentFragment = setIndexFragment()

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
            itemTitle.setTextSize(bean.normalSize)

            itemTitle.setText(bean.title)
            if (i == mCurrentFragment) {
                itemIcon.setImageResource(bean.activeIcon)
                itemTitle.setTextColor(bean.activeTextColor)
                itemTitle.setTextSize(bean.activeSize)
            }
        }
        showFragment(-1, mCurrentFragment)
    }


    private fun showFragment(oldIndex: Int, newIndex: Int) {
        if (oldIndex == newIndex) return
        if (!this::mManager.isInitialized) {
            mManager = supportFragmentManager
        }
        var ft = mManager.beginTransaction()
        if (oldIndex > 0) {
            ft.hide(mItemFragments[oldIndex])
        }
        val tag: String = mItemFragments[newIndex].javaClass.name
        var frag: Fragment? = null
        frag = mManager.findFragmentByTag(tag)
        if (frag != null) {
            ft.show(frag)
        } else {
            ft.add(
                R.id.bottom_bar_fragment_container
                , mItemFragments[newIndex], tag
            )
        }
        ft.commitAllowingStateLoss()
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
            itemTitle.setTextSize(bean.normalSize)
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
        itemIcon.setImageResource(bean.activeIcon)
        itemTitle.setTextColor(bean.activeTextColor)
        itemTitle.setTextSize(bean.activeSize)
    }

    override fun onClick(v: View) {
        val tabIndex = v.tag as Int
        changeColor(tabIndex)
        //展示和隐藏内容部分的
        showFragment(mCurrentFragment, tabIndex)
        //先后顺序不能错
        mCurrentFragment = tabIndex
    }
}