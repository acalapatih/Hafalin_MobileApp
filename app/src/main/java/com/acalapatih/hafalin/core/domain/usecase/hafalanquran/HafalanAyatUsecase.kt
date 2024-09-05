package com.acalapatih.hafalin.core.domain.usecase.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanAyatModel
import kotlinx.coroutines.flow.Flow

interface HafalanAyatUsecase {
    fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<HafalanAyatModel>>
}