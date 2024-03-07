package com.acalapatih.oneayat.core.domain.repository.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaSuratModel
import kotlinx.coroutines.flow.Flow

interface BacaSuratRepository {
    fun getListAyatSurat(nomorSurat: String): Flow<Resource<BacaSuratModel>>
}