package com.acalapatih.oneayat.ui.hafalanquran.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.local.entity.AyatDihafal
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanQuranModel
import com.acalapatih.oneayat.core.domain.repository.bookmark.AyatDihafalRepository
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.HafalanQuranUsecase
import kotlinx.coroutines.launch

class HafalanQuranViewModel(
    application: Application,
    private val usecase: HafalanQuranUsecase
): ViewModel() {
    private val _getListSurah = MutableLiveData<Resource<HafalanQuranModel>>()
    val getListSurah: LiveData<Resource<HafalanQuranModel>> get() = _getListSurah

    private val ayatDihafalRepository: AyatDihafalRepository =
        AyatDihafalRepository(application)

    fun getListSurah() {
        viewModelScope.launch {
            usecase.getListSurat().collect{
                _getListSurah.value = it
            }
        }
    }

    val ayatDihafal: LiveData<AyatDihafal> = ayatDihafalRepository.getAyatDihafal()
}