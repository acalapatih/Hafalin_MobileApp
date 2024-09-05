package com.acalapatih.hafalin.core.domain.repository.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaAyatModel
import kotlinx.coroutines.flow.Flow

interface BacaAyatRepository {
    fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<BacaAyatModel>>
}