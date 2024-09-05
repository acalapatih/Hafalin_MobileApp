package com.acalapatih.hafalin.core.domain.repository.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.ListSuratModel
import kotlinx.coroutines.flow.Flow

interface ListSuratRepository {
    fun getListSurat(): Flow<Resource<ListSuratModel>>
}