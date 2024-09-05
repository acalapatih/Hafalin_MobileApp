package com.acalapatih.hafalin.core.domain.interactor.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanSuratModel
import com.acalapatih.hafalin.core.domain.repository.hafalanquran.HafalanSuratRepository
import com.acalapatih.hafalin.core.domain.usecase.hafalanquran.HafalanSuratUseCase
import kotlinx.coroutines.flow.Flow

class HafalanSuratInteractor(
    private val repository: HafalanSuratRepository
): HafalanSuratUseCase {
    override fun getListAyat(nomorSurat: String): Flow<Resource<HafalanSuratModel>> =
        repository.getListAyat(nomorSurat)
}