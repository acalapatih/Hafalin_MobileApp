package com.acalapatih.hafalin.ui.bacaquran.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.hafalin.BaseActivity
import com.acalapatih.hafalin.R
import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.data.source.local.preference.SettingPreferences
import com.acalapatih.hafalin.core.domain.model.bacaquran.ListSuratModel
import com.acalapatih.hafalin.core.factory.SettingViewModelFactory
import com.acalapatih.hafalin.databinding.ActivityBacaQuranBinding
import com.acalapatih.hafalin.ui.bacaquran.adapter.ListSuratAdapter
import com.acalapatih.hafalin.ui.bacaquran.viewmodel.ListSuratViewModel
import com.acalapatih.hafalin.ui.bookmark.activity.BookmarkActivity
import com.acalapatih.hafalin.ui.setting.SettingViewModel
import com.acalapatih.hafalin.utils.dataStore
import org.koin.androidx.viewmodel.ext.android.viewModel

class BacaQuranActivity : BaseActivity<ActivityBacaQuranBinding>(), ListSuratAdapter.OnUserClickListener {
    override fun getViewBinding(): ActivityBacaQuranBinding =
        ActivityBacaQuranBinding.inflate(layoutInflater)

    private val viewModel by viewModel<ListSuratViewModel>()
    private lateinit var listSuratAdapter: ListSuratAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        viewModel.getListSurat()

        initView()
        initObserver()
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
//                    val tabTextColors = ContextCompat.getColorStateList(this@BacaQuranActivity, R.color.white)
//                    tlBacaQuran.setTabTextColors(tabTextColors)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    icBack.setImageResource(R.drawable.ic_back_blue)
                    icBookmark.setImageResource(R.drawable.ic_bookmark_blue)
//                    val tabTextColors = ContextCompat.getColorStateList(this@BacaQuranActivity, R.color.black_040D12)
//                    tlBacaQuran.setTabTextColors(tabTextColors)
                }
            }
        }

        val layoutManager = LinearLayoutManager(this@BacaQuranActivity)
        val itemDecoration = DividerItemDecoration(this@BacaQuranActivity, layoutManager.orientation)
        with(binding.rvSurat) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
        }

//        val sectionsPagerAdapter = SectionsPagerAdapter(this@BacaQuranActivity)
//        val viewPager = binding.vpBacaQuran
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = binding.tlBacaQuran
//        TabLayoutMediator(tabs, viewPager) { tab, position ->
//            tab.text = resources.getString(TAB_TITLE[position])
//        }.attach()
    }

    private fun initObserver() {
        viewModel.getListSurat.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        getListSurah(data)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    model.message?.let {
                        println(it)
                        Toast.makeText(
                            this@BacaQuranActivity,
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun getListSurah(data: ListSuratModel) {
        val pengaturanPref = SettingPreferences.getInstance(this@BacaQuranActivity.dataStore)
        val settingViewModel = ViewModelProvider(
            this@BacaQuranActivity,
            SettingViewModelFactory(pengaturanPref)
        )[SettingViewModel::class.java]
        val listener = this

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                listSuratAdapter = ListSuratAdapter(data.listSurat, isDarkModeActive, listener)
                binding.rvSurat.adapter = listSuratAdapter
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                listSuratAdapter = ListSuratAdapter(data.listSurat, isDarkModeActive, listener)
                binding.rvSurat.adapter = listSuratAdapter
            }
        }
    }

    override fun onUserClicked(nomorSurat: String, clicked: String) {
        BacaSuratActivity.start(this@BacaQuranActivity, nomorSurat)
    }

    private fun initListener() {
        with(binding) {
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@BacaQuranActivity) {
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