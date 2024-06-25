package com.acalapatih.oneayat.ui.hafalanquran.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanSuratModel
import com.acalapatih.oneayat.databinding.ActivityHafalanSuratBinding
import com.acalapatih.oneayat.ui.bookmark.activity.BookmarkActivity
import com.acalapatih.oneayat.ui.hafalanquran.adapter.HafalanSuratAdapter
import com.acalapatih.oneayat.ui.hafalanquran.viewmodel.HafalanSuratViewModel
import com.acalapatih.oneayat.BaseActivity
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.core.domain.model.hafalanquran.RequestTokenModel
import com.acalapatih.oneayat.core.factory.SettingViewModelFactory
import com.acalapatih.oneayat.core.preference.SettingPreferences
import com.acalapatih.oneayat.ui.setting.SettingViewModel
import com.acalapatih.oneayat.utils.Const.NOMOR_SURAT
import com.acalapatih.oneayat.utils.dataStore
import org.koin.androidx.viewmodel.ext.android.viewModel

class HafalanSuratActivity : BaseActivity<ActivityHafalanSuratBinding>(), HafalanSuratAdapter.OnUserClickListener {

    private lateinit var hafalanSuratAdapter: HafalanSuratAdapter
    private val viewModel by viewModel<HafalanSuratViewModel>()
    private val nomorSurat by lazy { intent.getStringExtra(NOMOR_SURAT) }

    override fun getViewBinding(): ActivityHafalanSuratBinding =
        ActivityHafalanSuratBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        nomorSurat?.let { viewModel.getListAyat(it) }

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

        settingViewModel.getThemeSetting().observe(this@HafalanSuratActivity) { isDarkModeActive: Boolean ->
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

        val layoutManager = LinearLayoutManager(this@HafalanSuratActivity)
        val itemDecoration = DividerItemDecoration(this@HafalanSuratActivity, layoutManager.orientation)
        with(binding.rvHafalanAyat) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
        }
    }

    private fun initObserver() {
        viewModel.getListAyat.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        getListAyat(data)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    model.message?.let {
                        println(it)
                        showToast(
                            it, Toast.LENGTH_SHORT
                        )
                    }
                }
            }
        }

        viewModel.requestToken.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        setToken(data)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    model.message?.let {
                        println(it)
                        showToast(
                            it, Toast.LENGTH_SHORT
                        )
                    }
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun getListAyat(data: HafalanSuratModel) {
        if (data.namaSurat == "Al-Fatihah") {
            viewModel.getAllAlFatihah().observe(this) { alFatihah ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, alFatihah.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "An-Nas") {
            viewModel.getAllAnNas().observe(this) { anNas ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, anNas.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "Al-Falaq") {
            viewModel.getAllAlFalaq().observe(this) { alFalaq ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, alFalaq.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "Al-Ikhlas") {
            viewModel.getAllAlIkhlas().observe(this) { alIkhlas ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, alIkhlas.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "Al-Lahab") {
            viewModel.getAllAlLahab().observe(this) { alLahab ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, alLahab.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "An-Nasr") {
            viewModel.getAllAnNasr().observe(this) { anNasr ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, anNasr.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "Al-Kafirun") {
            viewModel.getAllAlKafirun().observe(this) { alKafirun ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, alKafirun.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "At-Takwir") {
            viewModel.getAllAtTakwir().observe(this) { atTakwir ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, atTakwir.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "An-Naba'") {
            viewModel.getAllAnNaba().observe(this) { anNaba ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, anNaba.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        if (data.namaSurat == "Al-Mulk") {
            viewModel.getAllAlMulk().observe(this) { alMulk ->
                hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, alMulk.size + 1, this)
                with(binding) {
                    tvSurat.text = data.namaSurat
                    rvHafalanAyat.adapter = hafalanSuratAdapter
                }
            }
        }
        else {
            hafalanSuratAdapter = HafalanSuratAdapter(data.listAyat, 1, this)
            with(binding) {
                tvSurat.text = data.namaSurat
                rvHafalanAyat.adapter = hafalanSuratAdapter
            }
        }
    }

    private fun setToken(data: RequestTokenModel) {
        viewModel.setToken(data.token)
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
                BookmarkActivity.start(this@HafalanSuratActivity)
            }
        }
    }

    override fun onUserClicked(nomorAyat: String, clicked: String) {
        nomorSurat?.let { HafalanAyatActivity.start(this@HafalanSuratActivity, it, nomorAyat) }
    }

    companion object {
        fun start(context: Context, nomorSurat: String) {
            val starter = Intent(context, HafalanSuratActivity::class.java)
                .putExtra(NOMOR_SURAT, nomorSurat)
            context.startActivity(starter)
        }
    }
}