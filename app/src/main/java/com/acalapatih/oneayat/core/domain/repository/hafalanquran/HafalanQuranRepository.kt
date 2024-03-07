package com.acalapatih.oneayat.core.domain.repository.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanQuranModel
import kotlinx.coroutines.flow.Flow

interface HafalanQuranRepository {
    fun getListSurat(): Flow<Resource<HafalanQuranModel>>
}