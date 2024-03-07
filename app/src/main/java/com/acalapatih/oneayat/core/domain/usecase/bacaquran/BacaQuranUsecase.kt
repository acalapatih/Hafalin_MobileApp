package com.acalapatih.oneayat.core.domain.usecase.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.ListSuratModel
import kotlinx.coroutines.flow.Flow

interface BacaQuranUsecase {
    fun getListSurat(): Flow<Resource<ListSuratModel>>
}