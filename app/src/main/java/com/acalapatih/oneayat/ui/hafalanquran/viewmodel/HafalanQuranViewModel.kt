package com.acalapatih.oneayat.ui.hafalanquran.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanQuranModel
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.HafalanQuranUsecase
import kotlinx.coroutines.launch

class HafalanQuranViewModel(
    private val usecase: HafalanQuranUsecase
): ViewModel() {
    private val _getListSurah = MutableLiveData<Resource<HafalanQuranModel>>()
    val getListSurah: LiveData<Resource<HafalanQuranModel>> get() = _getListSurah

    fun getListSurah() {
        viewModelScope.launch {
            usecase.getListSurat().collect{
                _getListSurah.value = it
            }
        }
    }
}