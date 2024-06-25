package com.acalapatih.oneayat.ui.bacaquran.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.acalapatih.oneayat.BaseActivity
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.core.factory.SettingViewModelFactory
import com.acalapatih.oneayat.core.preference.SettingPreferences
import com.acalapatih.oneayat.databinding.ActivityBacaQuranBinding
import com.acalapatih.oneayat.ui.bacaquran.SectionsPagerAdapter
import com.acalapatih.oneayat.ui.bookmark.activity.BookmarkActivity
import com.acalapatih.oneayat.ui.setting.SettingViewModel
import com.acalapatih.oneayat.utils.dataStore
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BacaQuranActivity : BaseActivity<ActivityBacaQuranBinding>() {
    override fun getViewBinding(): ActivityBacaQuranBinding =
        ActivityBacaQuranBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        initView()
        initListener()
    }

    private fun initView() {
        val pengaturanPref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pengaturanPref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this@BacaQuranActivity) { isDarkModeActive: Boolean ->
            with(binding) {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    icBack.setImageResource(R.drawable.ic_back_white)
                    icBookmark.setImageResource(R.drawable.ic_bookmark_white)
                    val tabTextColors = ContextCompat.getColorStateList(this@BacaQuranActivity, R.color.white)
                    tlBacaQuran.setTabTextColors(tabTextColors)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    icBack.setImageResource(R.drawable.ic_back_green)
                    icBookmark.setImageResource(R.drawable.ic_bookmark_green)
                    val tabTextColors = ContextCompat.getColorStateList(this@BacaQuranActivity, R.color.black_040D12)
                    tlBacaQuran.setTabTextColors(tabTextColors)
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this@BacaQuranActivity)
        val viewPager = binding.vpBacaQuran
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tlBacaQuran
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()
    }

    private fun initListener() {
        with(binding) {
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback {
                finish()
            }
            icBookmark.setOnClickListener {
                BookmarkActivity.start(this@BacaQuranActivity)
            }
        }
    }

    companion object {
        @JvmStatic
        fun start (context: Context) {
            val starter = Intent(context, BacaQuranActivity::class.java)
            context.startActivity(starter)
        }

        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2
        )
    }
}