package com.acalapatih.oneayat.ui.bookmark.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.oneayat.BaseActivity
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.core.factory.SettingViewModelFactory
import com.acalapatih.oneayat.core.data.source.local.preference.SettingPreferences
import com.acalapatih.oneayat.databinding.ActivityBookmarkBinding
import com.acalapatih.oneayat.ui.bookmark.adapter.BookmarkAdapter
import com.acalapatih.oneayat.ui.bookmark.viewmodel.BookmarkViewModel
import com.acalapatih.oneayat.ui.setting.SettingViewModel
import com.acalapatih.oneayat.utils.dataStore
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkActivity : BaseActivity<ActivityBookmarkBinding>() {

    private val viewModel by viewModel<BookmarkViewModel>()
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun getViewBinding(): ActivityBookmarkBinding =
        ActivityBookmarkBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

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

        settingViewModel.getThemeSetting().observe(this@BookmarkActivity) { isDarkModeActive: Boolean ->
            with(binding) {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    icBack.setImageResource(R.drawable.ic_back_white)
                    icTerakhirDibaca.setImageResource(R.drawable.ic_dibaca_white)
                    icAyatFavorit.setImageResource(R.drawable.ic_ayat_favorit_white)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    icBack.setImageResource(R.drawable.ic_back_green)
                    icTerakhirDibaca.setImageResource(R.drawable.ic_dibaca_green)
                    icAyatFavorit.setImageResource(R.drawable.ic_ayat_favorit_green)
                }
            }
        }

        bookmarkAdapter = BookmarkAdapter(this@BookmarkActivity)
        val layoutManager = LinearLayoutManager(this@BookmarkActivity)
        val itemDecoration = DividerItemDecoration(this@BookmarkActivity, layoutManager.orientation)
        with(binding.rvAyat) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
            adapter = bookmarkAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver() {
        viewModel.ayatDibaca.observe(this) { data ->
            if (data != null) {
                with(binding.tvAyatDibaca) {
                    text = "Q.S ${data.namaSurat} : ${data.nomorAyat}"

                    setOnClickListener {
                        AyatDisimpan.start(this@BookmarkActivity, data.nomorSurat, data.nomorAyat)
                    }
                }
            } else {
                binding.tvAyatDibaca.text = "Belum ada ayat yang ditandai"
            }
        }

        viewModel.getAllAyatFavorit().observe(this) { listAyatFavorit ->
            if (listAyatFavorit != null) {
                bookmarkAdapter.setListFavorite(listAyatFavorit)

                bookmarkAdapter.hapusAyatFavorit = { ayatFavorit ->
                    showDialogHapusAyatFavorit {
                        viewModel.delete(ayatFavorit)
                    }
                }
            } else {
                binding.tvNoDataAyatFavorit.isVisible = true
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
        }
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BookmarkActivity::class.java)
            context.startActivity(starter)
        }
    }
}