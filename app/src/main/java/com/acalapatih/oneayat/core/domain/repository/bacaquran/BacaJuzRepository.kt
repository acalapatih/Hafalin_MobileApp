package com.acalapatih.oneayat.core.domain.repository.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaJuzModel
import kotlinx.coroutines.flow.Flow

interface BacaJuzRepository {
    fun getListAyatJuz(nomorJuz: String): Flow<Resource<BacaJuzModel>>
}