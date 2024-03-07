package com.acalapatih.oneayat.core.domain.usecase.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanQuranModel
import kotlinx.coroutines.flow.Flow

interface HafalanQuranUsecase {
    fun getListSurat(): Flow<Resource<HafalanQuranModel>>
}