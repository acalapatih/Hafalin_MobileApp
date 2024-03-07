package com.acalapatih.oneayat.core.data.repositoryImpl.hafalanquran

import com.acalapatih.oneayat.core.data.NetworkOnlyResource
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.remote.RemoteDataSource
import com.acalapatih.oneayat.core.data.source.remote.network.ApiResponse
import com.acalapatih.oneayat.core.data.source.remote.response.GetAyatResponse
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanAyatModel
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.HafalanAyatRepository
import kotlinx.coroutines.flow.Flow

class HafalanAyatRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): HafalanAyatRepository {
    override fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<HafalanAyatModel>> =
        object : NetworkOnlyResource<HafalanAyatModel, GetAyatResponse>() {
            override fun loadFromNetwork(data: GetAyatResponse): Flow<HafalanAyatModel> =
                HafalanAyatModel.mapResponseToModel(data)

            override suspend fun createCall(): Flow<ApiResponse<GetAyatResponse>> =
                remoteDataSource.getAyat(nomorSurat, nomorAyat)
        }.asFlow()
}