package com.acalapatih.hafalin.core.domain.interactor.bacaquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.bacaquran.ListSuratModel
import com.acalapatih.hafalin.core.domain.repository.bacaquran.ListSuratRepository
import com.acalapatih.hafalin.core.domain.usecase.bacaquran.BacaQuranUsecase
import kotlinx.coroutines.flow.Flow

class ListSuratInteractor(
    private val repository: ListSuratRepository
): BacaQuranUsecase {
    override fun getListSurat(): Flow<Resource<ListSuratModel>> =
        repository.getListSurat()
}