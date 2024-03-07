package com.acalapatih.oneayat.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.acalapatih.oneayat.core.data.source.local.entity.AyatDibaca
import com.acalapatih.oneayat.core.data.source.local.entity.AyatDihafal
import com.acalapatih.oneayat.core.domain.repository.bookmark.AyatDibacaRepository
import com.acalapatih.oneayat.core.domain.repository.bookmark.AyatDihafalRepository

class HomeViewModel(
    application: Application
): ViewModel() {
    private val ayatDibacaRepository: AyatDibacaRepository =
        AyatDibacaRepository(application)

    private val ayatDihafalRepository: AyatDihafalRepository =
        AyatDihafalRepository(application)

    val ayatDibaca: LiveData<AyatDibaca> = ayatDibacaRepository.getAyatDibaca()

    val waktuHafalan: LiveData<String> = ayatDihafalRepository.getWaktuHafalan()

    val jumlahAyatDihafal: LiveData<Int> = ayatDihafalRepository.getJumlahAyatDihafal()

    val ayatDihafal: LiveData<AyatDihafal> = ayatDihafalRepository.getAyatDihafal()
}