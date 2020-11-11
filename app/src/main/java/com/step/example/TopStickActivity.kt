package com.step.example

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.step.gankall.R
import kotlinx.android.synthetic.main.activity_top_stick.*
import timber.log.Timber
import java.util.*


class TopStickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_stick)
        setUpView()

    }

    private fun setUpView() {
        var handler = Handler(object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                sr_refresh.isRefreshing = false
                Timber.d("收到消息啦 ")
                return true
            }
        })
        sr_refresh.isEnabled = true
        sr_refresh.setOnRefreshListener {
            handler.sendEmptyMessageDelayed(0, 5000)
        }
        tab_layout.addTab(tab_layout.newTab().setText("信息"))
        tab_layout.addTab(tab_layout.newTab().setText("详情"))
        tab_layout.addTab(tab_layout.newTab().setText("评价"))
        recy_view.adapter = RvAdapter()

        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                Timber.d("onOffsetChanged   $verticalOffset ")
            }
        });
        sr_refresh.setOnPreInterceptTouchEventDelegate { _ -> app_bar.top < 0 }
    }


    class RvAdapter : RecyclerView.Adapter<StickHolder> {
        val rnd = Random()

        constructor() : super()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickHolder {
            var textView = TextView(parent.context)
            textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
            textView.gravity = Gravity.CENTER
            val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            textView.setBackgroundColor(color)
            textView.textSize = 20f
            return StickHolder(textView)
        }

        override fun getItemCount() = 20

        override fun onBindViewHolder(holder: StickHolder, position: Int) {
            holder.bindData(position)
        }

        override fun onBindViewHolder(
            holder: StickHolder,
            position: Int,
            payloads: MutableList<Any>
        ) {
            super.onBindViewHolder(holder, position, payloads)
        }

        override fun onViewAttachedToWindow(holder: StickHolder) {
            super.onViewAttachedToWindow(holder)
        }

        override fun onViewDetachedFromWindow(holder: StickHolder) {
            super.onViewDetachedFromWindow(holder)
        }

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            super.onAttachedToRecyclerView(recyclerView)
        }

        override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
            super.onDetachedFromRecyclerView(recyclerView)
        }

        override fun onViewRecycled(holder: StickHolder) {
            super.onViewRecycled(holder)
        }


        override fun onFailedToRecycleView(holder: StickHolder): Boolean {
            return super.onFailedToRecycleView(holder)
        }












        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }

        override fun setHasStableIds(hasStableIds: Boolean) {
            super.setHasStableIds(hasStableIds)
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

    }


    class StickHolder : RecyclerView.ViewHolder {
        lateinit var mTextViewName: TextView

        constructor(itemView: View) : super(itemView) {
            mTextViewName = itemView as TextView
        }

        fun bindData(position: Int) {
            mTextViewName.text = "详情 - $position"
        }

    }
}
