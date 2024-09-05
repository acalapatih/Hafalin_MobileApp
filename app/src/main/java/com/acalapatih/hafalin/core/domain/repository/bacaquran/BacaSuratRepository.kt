package com.acalapatih.hafalin.core.domain.repository.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaSuratModel
import kotlinx.coroutines.flow.Flow

interface BacaSuratRepository {
    fun getListAyatSurat(nomorSurat: String): Flow<Resource<BacaSuratModel>>
}