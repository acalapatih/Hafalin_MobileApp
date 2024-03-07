package com.acalapatih.oneayat.ui.hafalanquran.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.local.entity.*
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanSuratModel
import com.acalapatih.oneayat.core.domain.model.hafalanquran.RequestTokenModel
import com.acalapatih.oneayat.core.domain.repository.bookmark.SuratDihafalRepository
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.HafalanSuratUseCase
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.RequestTokenUsecase
import kotlinx.coroutines.launch

class HafalanSuratViewModel(
    application: Application,
    private val usecaseHafalanSurat: HafalanSuratUseCase,
    private val usecaseRequestToken: RequestTokenUsecase
): ViewModel() {
    private val _getListAyat = MutableLiveData<Resource<HafalanSuratModel>>()
    val getListAyat: LiveData<Resource<HafalanSuratModel>> get() = _getListAyat

    private val _requestToken = MutableLiveData<Resource<RequestTokenModel>>()
    val requestToken: LiveData<Resource<RequestTokenModel>> get() = _requestToken

    private val ayatDihafalRepository: SuratDihafalRepository =
        SuratDihafalRepository(application)

    fun getListAyat(nomorSurat: String) {
        viewModelScope.launch {
            usecaseHafalanSurat.getListAyat(nomorSurat).collect{
                _getListAyat.value = it
            }
        }
    }

    fun requestToken() {
        viewModelScope.launch {
            usecaseRequestToken.getToken().collect{
                _requestToken.value = it
            }
        }
    }

    fun setToken(token: String) {
        viewModelScope.launch{
            usecaseHafalanSurat.setToken(token)
        }
    }

    fun getAllAlFatihah(): LiveData<List<AlFatihah>> =
        ayatDihafalRepository.getAllAlFatihah()

    fun getAllAnNas(): LiveData<List<AnNas>> =
        ayatDihafalRepository.getAllAnNas()

    fun getAllAlFalaq(): LiveData<List<AlFalaq>> =
        ayatDihafalRepository.getAllAlFalaq()

    fun getAllAlIkhlas(): LiveData<List<AlIkhlas>> =
        ayatDihafalRepository.getAllAlIkhlas()

    fun getAllAtTakwir(): LiveData<List<AtTakwir>> =
        ayatDihafalRepository.getAllAtTakwir()

    fun getAllAnNaba(): LiveData<List<AnNaba>> =
        ayatDihafalRepository.getAllAnNaba()

    fun getAllAlMulk(): LiveData<List<AlMulk>> =
        ayatDihafalRepository.getAllAlMulk()

    fun getAllAlKahfi(): LiveData<List<AlKahfi>> =
        ayatDihafalRepository.getAllAlKahfi()
}