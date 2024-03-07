package com.acalapatih.oneayat.ui.bookmark.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.oneayat.BaseActivity
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.local.entity.AyatDibaca
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaSuratModel
import com.acalapatih.oneayat.databinding.ActivityAyatFavoritBinding
import com.acalapatih.oneayat.ui.bacaquran.adapter.BacaSuratAdapter
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.BacaSuratViewModel
import com.acalapatih.oneayat.utils.Const
import org.koin.androidx.viewmodel.ext.android.viewModel

class AyatDisimpan : BaseActivity<ActivityAyatFavoritBinding>(), BacaSuratAdapter.OnUserClickListener {

    private val viewModel by viewModel<BacaSuratViewModel>()
    private lateinit var bacaAyatAdapter: BacaSuratAdapter
    private val nomorSurat by lazy { intent.getStringExtra(Const.NOMOR_SURAT) }
    private val nomorAyat by lazy { intent.getStringExtra(Const.NOMOR_AYAT) }

    override fun getViewBinding(): ActivityAyatFavoritBinding =
        ActivityAyatFavoritBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        nomorSurat?.let { viewModel.getListAyat(it) }

        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this@AyatDisimpan)
        val itemDecoration = DividerItemDecoration(this@AyatDisimpan, layoutManager.orientation)
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
                        Toast.makeText(
                            this@AyatDisimpan,
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
    private fun getListAyat(data: BacaSuratModel) {
        bacaAyatAdapter = BacaSuratAdapter(
            this@AyatDisimpan,
            data.namaSurat,
            data.nomorSurat,
            data.listAyat,
            this
        )

        with(binding) {
            tvSurat.text = data.namaSurat
            tvInfoSurat.text = "${data.artiSurat} | ${data.jumlahAyat} Ayat"
            rvAyat.apply {
                adapter = bacaAyatAdapter
                if (scrollState == 0) {
                    nomorAyat?.let { smoothScrollToPosition(it.toInt()) }
                } else {
                    stopScroll()
                }
            }

            bacaAyatAdapter.ayatFavoritSelected = { ayatFavorit, nomorAyat, icFavorit ->
                showDialogAyatFavorit {
                    viewModel.insertAyatFavorit(ayatFavorit)
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
                BookmarkActivity.start(this@AyatDisimpan)
            }
        }
    }

    override fun onUserClickedTandai(ayatDibaca: AyatDibaca, clicked: String) {
        showDialogTerakhirDibaca {
            viewModel.insertAyatDibaca(ayatDibaca)
        }
    }

    override fun onUserClickedAudio(nomorAyat: String, audioAyatPlayer: MediaPlayer, clicked: String) {
        nomorSurat?.let { viewModel.getAyat(it, nomorAyat) }
        initObserverAudioAyat(audioAyatPlayer)
    }

    @SuppressLint("SetTextI18n")
    private fun initObserverAudioAyat(audioAyatPlayer: MediaPlayer) {
        viewModel.getAyat.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        with(binding) {
                            binding.cvAudioAyat.isVisible = true
                            binding.tvAudioAyat.text = "${data.namaSurat} : ${data.nomorAyat}"
                            icPlay.alpha = 0.5F
                            icPlay.isEnabled = false

                            icPause.setOnClickListener {
                                audioAyatPlayer.pause()

                                icPause.alpha = 0.5F
                                icPause.isEnabled = false

                                icPlay.alpha = 1F
                                icPlay.isEnabled = true
                            }

                            icReset.setOnClickListener {
                                if (audioAyatPlayer.isPlaying) {
                                    audioAyatPlayer.pause()
                                }

                                audioAyatPlayer.reset()
                                audioAyatPlayer.setDataSource(this@AyatDisimpan, data.audioAyat.toUri())
                                audioAyatPlayer.prepare()

                                icPlay.alpha = 1F
                                icPlay.isEnabled = true

                                icPause.alpha = 0.5F
                                icPause.isEnabled = false
                            }

                            icPlay.setOnClickListener {
                                audioAyatPlayer.start()
                                icPlay.alpha = 0.5F
                                icPlay.isEnabled = false

                                icPause.alpha = 1F
                                icPause.isEnabled = true
                            }

                            btnStop.setOnClickListener {
                                audioAyatPlayer.stop()
                                audioAyatPlayer.release()
                                cvAudioAyat.isVisible = false
                            }
                        }
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

    companion object {
        fun start(context: Context, nomorSurat: String, nomorAyat: String) {
            val starter = Intent(context, AyatDisimpan::class.java)
                .putExtra(Const.NOMOR_SURAT,  nomorSurat)
                .putExtra(Const.NOMOR_AYAT,  nomorAyat)
            context.startActivity(starter)
        }
    }
}