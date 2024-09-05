package com.acalapatih.hafalin.core.domain.interactor.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaJuzModel
import com.acalapatih.hafalin.core.domain.repository.bacaquran.BacaJuzRepository
import com.acalapatih.hafalin.core.domain.usecase.bacaquran.BacaJuzUsecase
import kotlinx.coroutines.flow.Flow

class BacaJuzInteractor(
    private val repository: BacaJuzRepository
): BacaJuzUsecase {
    override fun getListAyatJuz(nomorJuz: String): Flow<Resource<BacaJuzModel>> =
        repository.getListAyatJuz(nomorJuz)
}