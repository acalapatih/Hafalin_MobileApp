package com.acalapatih.hafalin.core.data.repositoryImpl.bacaquran

import com.acalapatih.hafalin.core.data.NetworkOnlyResource
import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.data.source.remote.RemoteDataSource
import com.acalapatih.hafalin.core.data.source.remote.network.ApiResponse
import com.acalapatih.hafalin.core.data.source.remote.response.GetListAyatResponse
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaSuratModel
import com.acalapatih.hafalin.core.domain.repository.bacaquran.BacaSuratRepository
import kotlinx.coroutines.flow.Flow

class BacaSuratRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): BacaSuratRepository {
    override fun getListAyatSurat(nomorSurat: String): Flow<Resource<BacaSuratModel>> =
        object : NetworkOnlyResource<BacaSuratModel, GetListAyatResponse>() {
            override fun loadFromNetwork(data: GetListAyatResponse): Flow<BacaSuratModel> =
                BacaSuratModel.GetListAyat.mapResponseToModel(data)

            override suspend fun createCall(): Flow<ApiResponse<GetListAyatResponse>> =
                remoteDataSource.getListAyatSurat(nomorSurat)
        }.asFlow()
}