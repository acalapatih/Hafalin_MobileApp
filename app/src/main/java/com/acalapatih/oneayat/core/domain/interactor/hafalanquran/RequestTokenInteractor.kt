package com.acalapatih.oneayat.core.domain.interactor.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.RequestTokenModel
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.RequestTokenRepository
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.RequestTokenUsecase
import kotlinx.coroutines.flow.Flow

class RequestTokenInteractor(
    private val repository: RequestTokenRepository
): RequestTokenUsecase {
    override fun getToken(): Flow<Resource<RequestTokenModel>> =
        repository.getToken()
}