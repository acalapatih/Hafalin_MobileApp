package com.acalapatih.hafalin.core.domain.usecase.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaJuzModel
import kotlinx.coroutines.flow.Flow

interface BacaJuzUsecase {
    fun getListAyatJuz(nomorJuz: String): Flow<Resource<BacaJuzModel>>
}