package com.acalapatih.hafalin.core.data.repositoryImpl.hafalanquran

import com.acalapatih.hafalin.core.data.NetworkOnlyResource
import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.data.source.remote.RemoteDataSource
import com.acalapatih.hafalin.core.data.source.remote.network.ApiResponse
import com.acalapatih.hafalin.core.data.source.remote.response.GetListSuratResponse
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanQuranModel
import com.acalapatih.hafalin.core.domain.repository.hafalanquran.HafalanQuranRepository
import kotlinx.coroutines.flow.Flow

class HafalanQuranRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): HafalanQuranRepository {
    override fun getListSurat(): Flow<Resource<HafalanQuranModel>> =
        object : NetworkOnlyResource<HafalanQuranModel, GetListSuratResponse>() {
            override fun loadFromNetwork(data: GetListSuratResponse): Flow<HafalanQuranModel> =
                HafalanQuranModel.GetListSurat.mapResponseToModel(data)

            override suspend fun createCall(): Flow<ApiResponse<GetListSuratResponse>> =
                remoteDataSource.getListSurah()
        }.asFlow()
}