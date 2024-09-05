package com.acalapatih.hafalin.core.domain.usecase.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanQuranModel
import kotlinx.coroutines.flow.Flow

interface HafalanQuranUsecase {
    fun getListSurat(): Flow<Resource<HafalanQuranModel>>
}