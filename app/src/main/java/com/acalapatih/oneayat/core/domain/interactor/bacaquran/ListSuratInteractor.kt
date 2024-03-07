package com.acalapatih.oneayat.core.domain.interactor.bacaquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.ListSuratModel
import com.acalapatih.oneayat.core.domain.repository.bacaquran.ListSuratRepository
import com.acalapatih.oneayat.core.domain.usecase.bacaquran.BacaQuranUsecase
import kotlinx.coroutines.flow.Flow

class ListSuratInteractor(
    private val repository: ListSuratRepository
): BacaQuranUsecase {
    override fun getListSurat(): Flow<Resource<ListSuratModel>> =
        repository.getListSurat()
}