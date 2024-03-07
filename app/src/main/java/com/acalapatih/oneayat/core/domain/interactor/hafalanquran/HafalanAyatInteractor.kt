package com.acalapatih.oneayat.core.domain.interactor.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanAyatModel
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.HafalanAyatRepository
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.HafalanAyatUsecase
import kotlinx.coroutines.flow.Flow

class HafalanAyatInteractor(
    private val repository: HafalanAyatRepository
): HafalanAyatUsecase {
    override fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<HafalanAyatModel>> =
        repository.getAyat(nomorSurat, nomorAyat)
}