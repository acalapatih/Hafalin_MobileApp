package com.acalapatih.oneayat.core.domain.repository.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanSuratModel
import kotlinx.coroutines.flow.Flow

interface HafalanSuratRepository {
    fun getListAyat(nomorSurat: String): Flow<Resource<HafalanSuratModel>>
}