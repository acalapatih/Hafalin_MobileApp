package com.acalapatih.oneayat.core.domain.usecase.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaAyatModel
import kotlinx.coroutines.flow.Flow

interface BacaAyatUsecase {
    fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<BacaAyatModel>>
}