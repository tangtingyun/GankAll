package com.step.gankall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import android.R


class MainActivity : AppCompatActivity() {
    lateinit var appBarConfig: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            drawerLayout,
            Navigation.findNavController(this, R.id.hostFragment)
        )
    }

    fun setUpNavController() {
        val navController = findNavController(R.id.hostFragment)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        NavigationUI.setupActionBarWithNavController(
            this, navController, drawerLayout
        )
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.gank -> navController.navigate(R.id.gankFragment)
                R.id.about -> navController.navigate(R.id.aboutFragment)
            }

            true
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
