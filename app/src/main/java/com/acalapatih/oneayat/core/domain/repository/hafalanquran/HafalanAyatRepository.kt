package com.acalapatih.oneayat.core.domain.repository.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanAyatModel
import kotlinx.coroutines.flow.Flow

interface HafalanAyatRepository {
    fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<HafalanAyatModel>>
}