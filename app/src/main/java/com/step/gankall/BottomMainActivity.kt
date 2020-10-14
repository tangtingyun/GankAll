package com.step.gankall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_bottom_main.*

class BottomMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_main)
        bottom_navigation.setupWithNavController(findNavController(R.id.bottom_hostFragment))
    }
}
