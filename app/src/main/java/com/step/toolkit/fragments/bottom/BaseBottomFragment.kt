package com.step.toolkit.fragments.bottom

import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.step.toolkit.fragments.BaseFragment

abstract class BaseBottomFragment : BaseFragment(), View.OnClickListener {

    private val mTabBean = ArrayList<BottomTabBean>()
    val mItemFragments = ArrayList<BottomItemFragment>()

    private val mItems = LinkedHashMap<BottomTabBean, BottomItemFragment>()

    private var mCurrentFragment = 0;

    // 设置首页一打开展示哪个fragment
    private var mIndexFragment = 0;

    private var mClickedColor = Color.RED

    private lateinit var mBottomBar: LinearLayoutCompat


    abstract fun setItems(builder: ItemBuilder)
            : LinkedHashMap<BottomTabBean, BottomItemFragment>
}