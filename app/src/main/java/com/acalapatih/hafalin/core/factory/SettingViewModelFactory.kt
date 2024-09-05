package com.acalapatih.hafalin.core.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.acalapatih.hafalin.core.data.source.local.preference.SettingPreferences
import com.acalapatih.hafalin.ui.setting.SettingViewModel

class SettingViewModelFactory(
    private val pref: SettingPreferences
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}