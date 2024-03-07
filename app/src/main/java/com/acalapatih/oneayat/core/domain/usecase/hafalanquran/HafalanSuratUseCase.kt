package com.acalapatih.oneayat.core.domain.usecase.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanSuratModel
import kotlinx.coroutines.flow.Flow

interface HafalanSuratUseCase {
    fun getListAyat(nomorSurat: String): Flow<Resource<HafalanSuratModel>>

    fun setToken(token: String)
}