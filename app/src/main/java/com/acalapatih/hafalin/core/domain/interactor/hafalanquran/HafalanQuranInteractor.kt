package com.acalapatih.hafalin.core.domain.interactor.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanQuranModel
import com.acalapatih.hafalin.core.domain.repository.hafalanquran.HafalanQuranRepository
import com.acalapatih.hafalin.core.domain.usecase.hafalanquran.HafalanQuranUsecase
import kotlinx.coroutines.flow.Flow

class HafalanQuranInteractor(
    private val repository: HafalanQuranRepository
) : HafalanQuranUsecase {
    override fun getListSurat(): Flow<Resource<HafalanQuranModel>> =
        repository.getListSurat()
}