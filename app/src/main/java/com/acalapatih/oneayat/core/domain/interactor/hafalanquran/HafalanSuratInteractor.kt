package com.acalapatih.oneayat.core.domain.interactor.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanSuratModel
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.HafalanSuratRepository
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.HafalanSuratUseCase
import kotlinx.coroutines.flow.Flow

class HafalanSuratInteractor(
    private val repository: HafalanSuratRepository
): HafalanSuratUseCase {
    override fun getListAyat(nomorSurat: String): Flow<Resource<HafalanSuratModel>> =
        repository.getListAyat(nomorSurat)
}