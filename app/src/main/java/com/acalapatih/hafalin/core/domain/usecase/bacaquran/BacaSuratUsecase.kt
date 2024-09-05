package com.acalapatih.hafalin.core.domain.usecase.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaSuratModel
import kotlinx.coroutines.flow.Flow

interface BacaSuratUsecase {
    fun getListAyatSurat(nomorSurat: String): Flow<Resource<BacaSuratModel>>
}