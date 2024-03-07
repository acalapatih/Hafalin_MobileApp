package com.acalapatih.oneayat.ui.hafalanquran.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.local.entity.*
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanAyatModel
import com.acalapatih.oneayat.core.domain.model.hafalanquran.SpeechToTextModel
import com.acalapatih.oneayat.core.domain.repository.bookmark.AyatDihafalRepository
import com.acalapatih.oneayat.core.domain.repository.bookmark.SuratDihafalRepository
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.HafalanAyatUsecase
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.SpeechToTextUsecase
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

class HafalanAyatViewModel(
    application: Application,
    private val usecaseHafalanAyat: HafalanAyatUsecase,
    private val usecaseSpeechToText: SpeechToTextUsecase,
): ViewModel() {
    private val _getAyat = MutableLiveData<Resource<HafalanAyatModel>>()
    val getAyat: LiveData<Resource<HafalanAyatModel>> get() = _getAyat

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    private val _postSpeechToText = MutableLiveData<Resource<SpeechToTextModel>>()
    val postSpeechToText: LiveData<Resource<SpeechToTextModel>> get() = _postSpeechToText

    private val suratDihafalRepository: SuratDihafalRepository =
        SuratDihafalRepository(application)

    private val ayatDihafalRepository: AyatDihafalRepository =
        AyatDihafalRepository(application)

    fun getAyat(nomorSurat: String, nomorAyat: String) {
        viewModelScope.launch {
            usecaseHafalanAyat.getAyat(nomorSurat, nomorAyat).collect{
                _getAyat.value = it
            }
        }
    }

    fun getToken() {
        _token.value = usecaseSpeechToText.getToken()
    }

    fun postSpeechToText(requestBody: RequestBody) {
        viewModelScope.launch {
            usecaseSpeechToText.postSpeechToText(requestBody).collect{
                _postSpeechToText.value = it
            }
        }
    }

    fun insertAlFatihah(alFatihah: AlFatihah) {
        suratDihafalRepository.insertAlFatihah(alFatihah)
    }

    fun insertAnNas(anNas: AnNas) {
        suratDihafalRepository.insertAnNas(anNas)
    }

    fun insertAlFalaq(alFalaq: AlFalaq) {
        suratDihafalRepository.insertAlFalaq(alFalaq)
    }

    fun insertAlIkhlas(alIkhlas: AlIkhlas) {
        suratDihafalRepository.insertAlIkhlas(alIkhlas)
    }

    fun insertAtTakwir(atTakwir: AtTakwir) {
        suratDihafalRepository.insertAtTakwir(atTakwir)
    }

    fun insertAnNaba(anNaba: AnNaba) {
        suratDihafalRepository.insertAnNaba(anNaba)
    }

    fun insertAlMulk(alMulk: AlMulk) {
        suratDihafalRepository.insertAlMulk(alMulk)
    }

    fun insertAlKahfi(alKahfi: AlKahfi) {
        suratDihafalRepository.insertAlKahfi(alKahfi)
    }

    fun insertAyatDihafal(ayatDihafal: AyatDihafal) {
        ayatDihafalRepository.insertAyatDihafal(ayatDihafal)
    }
}