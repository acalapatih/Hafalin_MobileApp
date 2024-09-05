package com.acalapatih.hafalin.core.domain.usecase.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanSuratModel
import kotlinx.coroutines.flow.Flow

interface HafalanSuratUseCase {
    fun getListAyat(nomorSurat: String): Flow<Resource<HafalanSuratModel>>
}