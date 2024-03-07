package com.acalapatih.oneayat.core.data.repositoryImpl.bacaquran

import com.acalapatih.oneayat.core.data.NetworkOnlyResource
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.remote.RemoteDataSource
import com.acalapatih.oneayat.core.data.source.remote.network.ApiResponse
import com.acalapatih.oneayat.core.data.source.remote.response.GetAyatResponse
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaAyatModel
import com.acalapatih.oneayat.core.domain.repository.bacaquran.BacaAyatRepository
import kotlinx.coroutines.flow.Flow

class BacaAyatRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): BacaAyatRepository {
    override fun getAyat(nomorSurat: String, nomorAyat: String): Flow<Resource<BacaAyatModel>> =
        object : NetworkOnlyResource<BacaAyatModel, GetAyatResponse>() {
            override fun loadFromNetwork(data: GetAyatResponse): Flow<BacaAyatModel> =
                BacaAyatModel.mapResponseToModel(data)

            override suspend fun createCall(): Flow<ApiResponse<GetAyatResponse>> =
                remoteDataSource.getAyat(nomorSurat, nomorAyat)
        }.asFlow()
}