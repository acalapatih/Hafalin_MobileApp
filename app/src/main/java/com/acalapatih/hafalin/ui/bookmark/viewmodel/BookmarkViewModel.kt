package com.acalapatih.hafalin.ui.bookmark.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.acalapatih.hafalin.core.data.source.local.entity.AyatDibaca
import com.acalapatih.hafalin.core.data.source.local.entity.AyatFavorit
import com.acalapatih.hafalin.core.domain.repository.bookmark.AyatDibacaRepository
import com.acalapatih.hafalin.core.domain.repository.bookmark.AyatFavoritRepository

class BookmarkViewModel(
    application: Application
): ViewModel() {
    private val ayatDibacaRepository: AyatDibacaRepository =
        AyatDibacaRepository(application)

    private val ayatFavoritRepository: AyatFavoritRepository =
        AyatFavoritRepository(application)

    fun delete(ayatFavorit: AyatFavorit) {
        ayatFavoritRepository.delete(ayatFavorit)
    }

    val ayatDibaca: LiveData<AyatDibaca> = ayatDibacaRepository.getAyatDibaca()

    fun getAllAyatFavorit(): LiveData<List<AyatFavorit>> =
        ayatFavoritRepository.getAllAyatFavorit()
}
