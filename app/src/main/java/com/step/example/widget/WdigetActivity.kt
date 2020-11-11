package com.step.example.widget

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.step.example.widget.recyclerview.RecyclerView
import com.step.example.widget.recyclerview.RecyclerView.Adapter
import com.step.example.widget.refresh.MyAdapter
import com.step.example.widget.refresh.PullRefreshRecyclerView
import com.step.gankall.R
import timber.log.Timber


class WdigetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate   后执行了")
        setContentView(R.layout.activity_wdiget)

//        testResourses()
//        testRecycler()
        testRecyclerView()

    }

    private fun testRecyclerView() {
        var recyclerView: RecyclerView = findViewById<View>(R.id.table) as RecyclerView
        recyclerView.setAdapter(object : Adapter {
            override fun onCreateViewHodler(
                position: Int,
                convertView: View?,
                parent: ViewGroup?
            ): View? {
                var textView = TextView(this@WdigetActivity)
                textView.text = "网易课堂 $position"
                return textView
            }

            override fun onBinderViewHodler(
                position: Int,
                convertView: View?,
                parent: ViewGroup?
            ): View? {
                val textView =
                    convertView as TextView
                textView.text = "网易课堂 $position"
                return convertView
            }

            override fun getItemViewType(row: Int): Int {
                return 0
            }

            override fun getViewTypeCount() = 1

            override fun getCount() = 30

            override fun getHeight(index: Int): Int {
                return 100
            }
        })
    }

    private fun testRecycler() {
        var mPullRefreshRecyclerView =
            findViewById<View>(R.id.pull_refresh) as PullRefreshRecyclerView


        var mLayoutManager = LinearLayoutManager(this)
        var mMyAdapter = MyAdapter(this)
        mPullRefreshRecyclerView.setLayoutManager(mLayoutManager)
        mPullRefreshRecyclerView.setAdapter(mMyAdapter)

        mPullRefreshRecyclerView.setOnPullListener(object :
            PullRefreshRecyclerView.OnPullListener {
            override fun onRefresh() {
                Handler().postDelayed(Runnable {
                    mPullRefreshRecyclerView.refreshFinish()
                }, 3000)
            }

            override fun onLoadMore() {
                Handler().postDelayed(Runnable {
                    mPullRefreshRecyclerView.loadMroeFinish()
                }, 1500)
            }
        })
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
