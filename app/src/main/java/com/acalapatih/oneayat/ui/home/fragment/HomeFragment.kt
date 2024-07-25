package com.acalapatih.oneayat.ui.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acalapatih.oneayat.BaseActivity
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.core.factory.SettingViewModelFactory
import com.acalapatih.oneayat.core.data.source.local.preference.SettingPreferences
import com.acalapatih.oneayat.databinding.FragmentHomeBinding
import com.acalapatih.oneayat.ui.bacaquran.activity.BacaQuranActivity
import com.acalapatih.oneayat.ui.bookmark.activity.AyatDisimpan
import com.acalapatih.oneayat.ui.hafalanquran.activity.HafalanQuranActivity
import com.acalapatih.oneayat.ui.hafalanquran.activity.HafalanSuratActivity
import com.acalapatih.oneayat.ui.home.viewmodel.HomeViewModel
import com.acalapatih.oneayat.ui.setting.SettingViewModel
import com.acalapatih.oneayat.utils.dataStore
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        val pengaturanPref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pengaturanPref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            with(binding) {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    cvHeaderHome.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.blue_001C30)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    cvHeaderHome.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver() {
        viewModel.ayatDibaca.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                with(binding) {
                    tvAyatDibaca.text = "Q.S ${data.namaSurat} : ${data.nomorAyat}"
                    cvTerakhirDibaca.setOnClickListener {
                        AyatDisimpan.start(requireContext(), data.nomorSurat, data.nomorAyat)
                    }
                }
            } else {
                binding.tvAyatDibaca.text = "Belum ada ayat yang ditandai"
            }
        }

        viewModel.waktuHafalan.observe(viewLifecycleOwner) { waktuHafalan ->
            viewModel.jumlahAyatDihafal.observe(viewLifecycleOwner) { jumlahAyat ->
                if (waktuHafalan != null && jumlahAyat != null) {
                    with(binding) {
                        tvEmptyProgresHafalan.visibility = View.GONE
                        tvLabelProgresHafalan.isVisible = true
                        tvProgresHafalan.isVisible = true
                        icProgres.isVisible = true

                        val selisihHari = hitungJumlahHari(waktuHafalan) - jumlahAyat
                        when {
                            selisihHari <= 0 -> {
                                icProgres.setImageResource(R.drawable.ic_lancar)
                                tvProgresHafalan.text = "Hafalan Lancar"
                            }
                            selisihHari in 1..3 -> {
                                icProgres.setImageResource(R.drawable.ic_kuranglancar)
                                tvProgresHafalan.text = "Hafalan Kurang Lancar"
                            }
                            else -> {
                                icProgres.setImageResource(R.drawable.ic_tidaklancar)
                                tvProgresHafalan.text = "Hafalan Tidak Lancar"
                            }
                        }
                    }
                }
            }
        }

        viewModel.ayatDihafal.observe(viewLifecycleOwner) { ayatDihafal ->
            if (ayatDihafal != null) {
                with(binding) {
                    tvSuratDihafal.text = "Q.S ${ayatDihafal.namaSurat} : ${ayatDihafal.nomorAyat}"
                    cvTerakhirDihafal.setOnClickListener {
                        HafalanSuratActivity.start(requireContext(), ayatDihafal.nomorSurat)
                    }
                }
            } else {
                binding.tvSuratDihafal.text = "Belum ada ayat yang ditandai"
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun hitungJumlahHari(waktuHafalan: String): Long {
        val waktuFormat = SimpleDateFormat("HH:mm:ss, EEEE, dd/MM/yyyy")
        val waktuDate: Date = waktuFormat.parse(waktuHafalan) as Date

        val calendarHafalan = Calendar.getInstance()
        calendarHafalan.time = waktuDate

        val calendarHariIni = Calendar.getInstance()

        val selisihMillis = calendarHariIni.timeInMillis - calendarHafalan.timeInMillis
        val selisihHari = selisihMillis / (24 * 60 * 60 * 1000)

        println("Jumlah hari: $selisihHari hari")

        return selisihHari
    }

    private fun initListener() {
        with(binding) {
            imgBacaquran.setOnClickListener {
                BacaQuranActivity.start(requireContext())
            }
            imgHafalanquran.setOnClickListener {
                HafalanQuranActivity.start(requireContext())
            }
            icProgres.setOnClickListener {
                showDialogStatusHafalan()
            }
        }
    }

    private fun showDialogStatusHafalan() {
        if (requireActivity() is BaseActivity<*>) {
            val baseActivity = requireActivity() as BaseActivity<*>
            baseActivity.showDialogStatusHafalan()
        }
    }
}