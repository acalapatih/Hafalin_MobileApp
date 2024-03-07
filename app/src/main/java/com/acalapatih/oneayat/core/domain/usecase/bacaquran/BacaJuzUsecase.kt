package com.acalapatih.oneayat.core.domain.usecase.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaJuzModel
import kotlinx.coroutines.flow.Flow

interface BacaJuzUsecase {
    fun getListAyatJuz(nomorJuz: String): Flow<Resource<BacaJuzModel>>
}