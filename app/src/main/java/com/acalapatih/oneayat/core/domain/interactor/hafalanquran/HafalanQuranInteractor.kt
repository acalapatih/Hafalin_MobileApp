package com.acalapatih.oneayat.core.domain.interactor.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanQuranModel
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.HafalanQuranRepository
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.HafalanQuranUsecase
import kotlinx.coroutines.flow.Flow

class HafalanQuranInteractor(
    private val repository: HafalanQuranRepository
) : HafalanQuranUsecase {
    override fun getListSurat(): Flow<Resource<HafalanQuranModel>> =
        repository.getListSurat()
}