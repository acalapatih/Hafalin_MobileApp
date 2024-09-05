package com.acalapatih.hafalin.ui.bacaquran.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.hafalin.databinding.ActivityBacaJuzBinding
import com.acalapatih.hafalin.ui.bookmark.activity.BookmarkActivity
import com.acalapatih.hafalin.BaseActivity
import com.acalapatih.hafalin.R
import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaJuzModel
import com.acalapatih.hafalin.core.factory.SettingViewModelFactory
import com.acalapatih.hafalin.core.data.source.local.preference.SettingPreferences
import com.acalapatih.hafalin.ui.bacaquran.adapter.BacaJuzAdapter
import com.acalapatih.hafalin.ui.bacaquran.viewmodel.BacaJuzViewModel
import com.acalapatih.hafalin.ui.setting.SettingViewModel
import com.acalapatih.hafalin.utils.Const.INFO_JUZ
import com.acalapatih.hafalin.utils.Const.NOMOR_JUZ
import com.acalapatih.hafalin.utils.dataStore
import org.koin.androidx.viewmodel.ext.android.viewModel

class BacaJuzActivity : BaseActivity<ActivityBacaJuzBinding>() {

    private val viewModel by viewModel<BacaJuzViewModel>()
    private lateinit var bacaJuzAdapter: BacaJuzAdapter
    private val nomorJuz by lazy { intent.getStringExtra(NOMOR_JUZ) }
    private val infoJuz by lazy { intent.getStringExtra(INFO_JUZ) }

    override fun getViewBinding(): ActivityBacaJuzBinding =
        ActivityBacaJuzBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        nomorJuz?.let { viewModel.getListAyat(it) }

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

        settingViewModel.getThemeSetting().observe(this@BacaJuzActivity) { isDarkModeActive: Boolean ->
            with(binding) {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    icBack.setImageResource(R.drawable.ic_back_white)
                    icBookmark.setImageResource(R.drawable.ic_bookmark_white)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    icBack.setImageResource(R.drawable.ic_back_green)
                    icBookmark.setImageResource(R.drawable.ic_bookmark_green)
                }
            }
        }

        val layoutManager = LinearLayoutManager(this@BacaJuzActivity)
        val itemDecoration = DividerItemDecoration(this@BacaJuzActivity, layoutManager.orientation)
        with(binding.rvAyat) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
        }
    }

    private fun initObserver() {
        viewModel.getListAyat.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                    binding.imgHeaderBacaQuran.visibility = View.GONE
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        getListAyat(data)
                        binding.imgHeaderBacaQuran.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    model.message?.let {
                        println(it)
                        Toast.makeText(
                            this@BacaJuzActivity,
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

    @SuppressLint("SetTextI18n")
    private fun getListAyat(data: BacaJuzModel) {
        val pengaturanPref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pengaturanPref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this@BacaJuzActivity) { isDarkModeActive: Boolean ->
            with(binding) {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    icBack.setImageResource(R.drawable.ic_back_white)
                    icBookmark.setImageResource(R.drawable.ic_bookmark_white)
                    imgHeaderBacaQuran.setImageResource(R.drawable.bg_header_bacaquran_dark)
                    bacaJuzAdapter = BacaJuzAdapter(
                        this@BacaJuzActivity,
                        data.listAyat,
                        isDarkModeActive
                    )
                    tvJuz.text = "Juz ${data.nomorJuz}"
                    tvInfoJuz.text = "$infoJuz"
                    rvAyat.adapter = bacaJuzAdapter
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    icBack.setImageResource(R.drawable.ic_back_green)
                    icBookmark.setImageResource(R.drawable.ic_bookmark_green)
                    imgHeaderBacaQuran.setImageResource(R.drawable.bg_header_bacaquran_light)
                    bacaJuzAdapter = BacaJuzAdapter(
                        this@BacaJuzActivity,
                        data.listAyat,
                        isDarkModeActive
                    )
                    tvJuz.text = "Juz ${data.nomorJuz}"
                    tvInfoJuz.text = "$infoJuz"
                    rvAyat.adapter = bacaJuzAdapter
                }
            }
        }
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
                BookmarkActivity.start(this@BacaJuzActivity)
            }
        }
    }

    companion object {
        fun start(context: Context, nomorJuz: String, infoJuz: String) {
            val starter = Intent(context, BacaJuzActivity::class.java)
                .putExtra(NOMOR_JUZ, nomorJuz)
                .putExtra(INFO_JUZ, infoJuz)
            context.startActivity(starter)
        }
    }
}