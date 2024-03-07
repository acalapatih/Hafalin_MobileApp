package com.acalapatih.oneayat.core.domain.interactor.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaAyatModel
import com.acalapatih.oneayat.core.domain.repository.bacaquran.BacaAyatRepository
import com.acalapatih.oneayat.core.domain.usecase.bacaquran.BacaAyatUsecase
import kotlinx.coroutines.flow.Flow

class BacaAyatInteractor(
    private val repository: BacaAyatRepository
): BacaAyatUsecase {
    override fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<BacaAyatModel>> =
        repository.getAyat(nomorSurat, nomorAyat)
}