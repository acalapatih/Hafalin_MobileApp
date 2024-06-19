package com.acalapatih.oneayat.ui.home.activity

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.databinding.ActivityHomeBinding
import com.acalapatih.oneayat.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity: BaseActivity<ActivityHomeBinding>() {
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView
    private lateinit var menu: Menu

    override fun getViewBinding(): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        initBottomNav()
    }

    private fun initBottomNav() {
        navView = binding.navView
        menu = navView.menu
        navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)
        navController.setGraph(R.navigation.app_navigation)

        when (intent.getStringExtra("action")) {
            "home" -> {
                navController.navigate(R.id.navigation_home)
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_selected_green)
                menu.findItem(R.id.navigation_quran).setIcon(R.drawable.ic_quran)
                menu.findItem(R.id.navigation_bookmark).setIcon(R.drawable.ic_bookmark_unselected_green)
                menu.findItem(R.id.navigation_setting).setIcon(R.drawable.ic_setting)
            }
            "quran" -> {
                navController.navigate(R.id.navigation_quran)
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_green)
                menu.findItem(R.id.navigation_quran).setIcon(R.drawable.ic_quran_selected)
                menu.findItem(R.id.navigation_bookmark).setIcon(R.drawable.ic_bookmark_unselected_green)
                menu.findItem(R.id.navigation_setting).setIcon(R.drawable.ic_setting)
            }
            "penanda" -> {
                navController.navigate(R.id.navigation_bookmark)
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_green)
                menu.findItem(R.id.navigation_quran).setIcon(R.drawable.ic_quran)
                menu.findItem(R.id.navigation_bookmark).setIcon(R.drawable.ic_bookmark_selected_green)
                menu.findItem(R.id.navigation_setting).setIcon(R.drawable.ic_setting)
            }
            else -> {
                navController.navigate(R.id.navigation_setting)
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_green)
                menu.findItem(R.id.navigation_quran).setIcon(R.drawable.ic_quran)
                menu.findItem(R.id.navigation_bookmark).setIcon(R.drawable.ic_bookmark_unselected_green)
                menu.findItem(R.id.navigation_setting).setIcon(R.drawable.ic_setting_selected)
            }
        }

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    item.setIcon(R.drawable.ic_home_selected_green)
                    menu.findItem(R.id.navigation_quran).setIcon(R.drawable.ic_quran)
                    menu.findItem(R.id.navigation_bookmark).setIcon(R.drawable.ic_bookmark_unselected_green)
                    menu.findItem(R.id.navigation_setting).setIcon(R.drawable.ic_setting)
                    navController.navigate(R.id.navigation_home)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_quran -> {
                    item.setIcon(R.drawable.ic_quran_selected)
                    menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_green)
                    menu.findItem(R.id.navigation_bookmark).setIcon(R.drawable.ic_bookmark_unselected_green)
                    menu.findItem(R.id.navigation_setting).setIcon(R.drawable.ic_setting)
                    navController.navigate(R.id.navigation_quran)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_bookmark -> {
                    item.setIcon(R.drawable.ic_bookmark_selected_green)
                    menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_green)
                    menu.findItem(R.id.navigation_quran).setIcon(R.drawable.ic_quran)
                    menu.findItem(R.id.navigation_setting).setIcon(R.drawable.ic_setting)
                    navController.navigate(R.id.navigation_bookmark)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_setting -> {
                    item.setIcon(R.drawable.ic_setting_selected)
                    menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_green)
                    menu.findItem(R.id.navigation_quran).setIcon(R.drawable.ic_quran)
                    menu.findItem(R.id.navigation_bookmark).setIcon(R.drawable.ic_bookmark_unselected_green)
                    navController.navigate(R.id.navigation_setting)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, value: String? = "") {
            val starter = Intent(context, HomeActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("action", value)
            context.startActivity(starter)
        }
    }
}