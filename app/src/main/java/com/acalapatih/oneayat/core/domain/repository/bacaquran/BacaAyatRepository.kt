package com.acalapatih.oneayat.core.domain.repository.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaAyatModel
import kotlinx.coroutines.flow.Flow

interface BacaAyatRepository {
    fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<BacaAyatModel>>
}