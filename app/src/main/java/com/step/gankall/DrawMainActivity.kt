package com.step.gankall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import kotlinx.android.synthetic.main.activity_main.*


class DrawMainActivity : AppCompatActivity() {
    lateinit var appBarConfig: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        /* return NavigationUI.navigateUp(
                 findNavController(R.id.hostFragment),
                 drawerLayout
             )*/
        val navigationController = findNavController(R.id.hostFragment)
        return navigationController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    fun setUpNavController() {
        val navController = findNavController(R.id.hostFragment)
        setSupportActionBar(toolbar)

        appBarConfig =
            AppBarConfiguration(
                setOf(
                    R.id.gankFragment,
                    R.id.cartFragment,
                    R.id.fileFragment,
                    R.id.aboutFragment
                ), drawerLayout
            )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
        NavigationUI.setupWithNavController(navigationView, navController)


        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.gank -> navController.navigate(R.id.gankFragment)
                R.id.cart -> navController.navigate(R.id.cartFragment)
                R.id.file -> navController.navigate(R.id.fileFragment)
                R.id.about -> navController.navigate(R.id.aboutFragment)

            }

            true
        }

        navigationView.setCheckedItem(R.id.gank)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
