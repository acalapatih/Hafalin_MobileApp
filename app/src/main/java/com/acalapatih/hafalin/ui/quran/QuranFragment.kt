package com.acalapatih.hafalin.ui.quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acalapatih.hafalin.core.factory.SettingViewModelFactory
import com.acalapatih.hafalin.core.data.source.local.preference.SettingPreferences
import com.acalapatih.hafalin.databinding.FragmentQuranBinding
import com.acalapatih.hafalin.ui.bacaquran.activity.BacaQuranActivity
import com.acalapatih.hafalin.ui.hafalanquran.activity.HafalanQuranActivity
import com.acalapatih.hafalin.ui.setting.SettingViewModel
import com.acalapatih.hafalin.utils.dataStore

class QuranFragment : Fragment() {

    private var _binding: FragmentQuranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {
        val pengaturanPref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pengaturanPref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun initListener() {
        with(binding) {
            cvWidgetBacaQuran.setOnClickListener {
                BacaQuranActivity.start(requireContext())
            }
            cvWidgetHafalanQuran.setOnClickListener {
                HafalanQuranActivity.start(requireContext())
            }
        }
    }
}