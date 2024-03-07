package com.acalapatih.oneayat.core.domain.usecase.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanAyatModel
import kotlinx.coroutines.flow.Flow

interface HafalanAyatUsecase {
    fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<HafalanAyatModel>>
}