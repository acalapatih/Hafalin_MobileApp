package com.acalapatih.hafalin.core.domain.interactor.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanAyatModel
import com.acalapatih.hafalin.core.domain.repository.hafalanquran.HafalanAyatRepository
import com.acalapatih.hafalin.core.domain.usecase.hafalanquran.HafalanAyatUsecase
import kotlinx.coroutines.flow.Flow

class HafalanAyatInteractor(
    private val repository: HafalanAyatRepository
): HafalanAyatUsecase {
    override fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<HafalanAyatModel>> =
        repository.getAyat(nomorSurat, nomorAyat)
}