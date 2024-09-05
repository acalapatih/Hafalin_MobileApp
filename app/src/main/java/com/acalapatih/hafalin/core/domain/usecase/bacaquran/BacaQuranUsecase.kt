package com.acalapatih.hafalin.core.domain.usecase.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.ListSuratModel
import kotlinx.coroutines.flow.Flow

interface BacaQuranUsecase {
    fun getListSurat(): Flow<Resource<ListSuratModel>>
}