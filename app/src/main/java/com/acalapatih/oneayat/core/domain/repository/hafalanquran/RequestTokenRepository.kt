package com.acalapatih.oneayat.core.domain.repository.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.RequestTokenModel
import kotlinx.coroutines.flow.Flow

interface RequestTokenRepository {
    fun getToken(): Flow<Resource<RequestTokenModel>>
}