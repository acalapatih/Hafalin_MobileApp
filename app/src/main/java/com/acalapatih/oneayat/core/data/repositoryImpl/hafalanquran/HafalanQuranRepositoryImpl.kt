package com.acalapatih.oneayat.core.data.repositoryImpl.hafalanquran

import com.acalapatih.oneayat.core.data.NetworkOnlyResource
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.remote.RemoteDataSource
import com.acalapatih.oneayat.core.data.source.remote.network.ApiResponse
import com.acalapatih.oneayat.core.data.source.remote.response.GetListSuratResponse
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanQuranModel
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.HafalanQuranRepository
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