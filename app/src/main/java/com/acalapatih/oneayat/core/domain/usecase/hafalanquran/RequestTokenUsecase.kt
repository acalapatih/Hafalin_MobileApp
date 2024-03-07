package com.acalapatih.oneayat.core.domain.usecase.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.RequestTokenModel
import kotlinx.coroutines.flow.Flow

interface RequestTokenUsecase {
    fun getToken(): Flow<Resource<RequestTokenModel>>
}