package com.acalapatih.hafalin.ui.hafalanquran.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.data.source.local.entity.AyatDihafal
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanQuranModel
import com.acalapatih.hafalin.core.domain.repository.bookmark.AyatDihafalRepository
import com.acalapatih.hafalin.core.domain.usecase.hafalanquran.HafalanQuranUsecase
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