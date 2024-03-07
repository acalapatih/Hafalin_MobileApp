package com.acalapatih.oneayat.core.domain.repository.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.ListSuratModel
import kotlinx.coroutines.flow.Flow

interface ListSuratRepository {
    fun getListSurat(): Flow<Resource<ListSuratModel>>
}