package com.acalapatih.hafalin.core.domain.interactor.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaSuratModel
import com.acalapatih.hafalin.core.domain.repository.bacaquran.BacaSuratRepository
import com.acalapatih.hafalin.core.domain.usecase.bacaquran.BacaSuratUsecase
import kotlinx.coroutines.flow.Flow

class BacaSuratInteractor(
    private val repository: BacaSuratRepository
): BacaSuratUsecase {
    override fun getListAyatSurat(nomorSurat: String): Flow<Resource<BacaSuratModel>> =
        repository.getListAyatSurat(nomorSurat)
}