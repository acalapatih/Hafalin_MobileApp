package com.acalapatih.oneayat.core.data.repositoryImpl.hafalanquran

import com.acalapatih.oneayat.core.data.NetworkOnlyResource
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.remote.RemoteDataSource
import com.acalapatih.oneayat.core.data.source.remote.network.ApiResponse
import com.acalapatih.oneayat.core.domain.model.hafalanquran.RequestTokenModel
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.RequestTokenRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class RequestTokenRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): RequestTokenRepository {
    override fun getToken(): Flow<Resource<RequestTokenModel>> =
        object : NetworkOnlyResource<RequestTokenModel, Call<String>>() {
            override fun loadFromNetwork(data: Call<String>): Flow<RequestTokenModel> =
                RequestTokenModel.mapResponseToModel(data)

            override suspend fun createCall(): Flow<ApiResponse<Call<String>>> =
                remoteDataSource.getToken()
        }.asFlow()
}