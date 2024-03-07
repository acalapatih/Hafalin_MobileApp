package com.acalapatih.oneayat.core.domain.interactor.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaJuzModel
import com.acalapatih.oneayat.core.domain.repository.bacaquran.BacaJuzRepository
import com.acalapatih.oneayat.core.domain.usecase.bacaquran.BacaJuzUsecase
import kotlinx.coroutines.flow.Flow

class BacaJuzInteractor(
    private val repository: BacaJuzRepository
): BacaJuzUsecase {
    override fun getListAyatJuz(nomorJuz: String): Flow<Resource<BacaJuzModel>> =
        repository.getListAyatJuz(nomorJuz)
}