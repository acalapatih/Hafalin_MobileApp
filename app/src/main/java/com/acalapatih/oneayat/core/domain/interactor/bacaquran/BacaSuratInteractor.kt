package com.acalapatih.oneayat.core.domain.interactor.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaSuratModel
import com.acalapatih.oneayat.core.domain.repository.bacaquran.BacaSuratRepository
import com.acalapatih.oneayat.core.domain.usecase.bacaquran.BacaSuratUsecase
import kotlinx.coroutines.flow.Flow

class BacaSuratInteractor(
    private val repository: BacaSuratRepository
): BacaSuratUsecase {
    override fun getListAyatSurat(nomorSurat: String): Flow<Resource<BacaSuratModel>> =
        repository.getListAyatSurat(nomorSurat)
}