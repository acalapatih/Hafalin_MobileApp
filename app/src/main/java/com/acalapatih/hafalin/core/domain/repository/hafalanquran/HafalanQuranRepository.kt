package com.acalapatih.hafalin.core.domain.repository.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanQuranModel
import kotlinx.coroutines.flow.Flow

interface HafalanQuranRepository {
    fun getListSurat(): Flow<Resource<HafalanQuranModel>>
}