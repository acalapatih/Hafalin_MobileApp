package com.acalapatih.oneayat.ui.setting

import androidx.lifecycle.*
import com.acalapatih.oneayat.core.preference.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(
    private val preferences: SettingPreferences
): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }
}