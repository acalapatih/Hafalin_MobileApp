package com.acalapatih.hafalin.core.domain.repository.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanSuratModel
import kotlinx.coroutines.flow.Flow

interface HafalanSuratRepository {
    fun getListAyat(nomorSurat: String): Flow<Resource<HafalanSuratModel>>
}