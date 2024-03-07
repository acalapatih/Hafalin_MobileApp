package com.acalapatih.oneayat.core.data.repositoryImpl.bacaquran

import com.acalapatih.oneayat.core.data.NetworkOnlyResource
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.data.source.remote.RemoteDataSource
import com.acalapatih.oneayat.core.data.source.remote.network.ApiResponse
import com.acalapatih.oneayat.core.data.source.remote.response.GetJuzResponse
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaJuzModel
import com.acalapatih.oneayat.core.domain.repository.bacaquran.BacaJuzRepository
import kotlinx.coroutines.flow.Flow

class BacaJuzRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): BacaJuzRepository {
    override fun getListAyatJuz(nomorJuz: String): Flow<Resource<BacaJuzModel>> =
        object : NetworkOnlyResource<BacaJuzModel, GetJuzResponse>() {
            override fun loadFromNetwork(data: GetJuzResponse): Flow<BacaJuzModel> =
                BacaJuzModel.GetListAyat.mapResponseToModel(data)

            override suspend fun createCall(): Flow<ApiResponse<GetJuzResponse>> =
                remoteDataSource.getListAyatJuz(nomorJuz)
        }.asFlow()
}