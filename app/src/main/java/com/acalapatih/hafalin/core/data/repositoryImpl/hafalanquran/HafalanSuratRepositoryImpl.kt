package com.acalapatih.hafalin.core.data.repositoryImpl.hafalanquran

import com.acalapatih.hafalin.core.data.NetworkOnlyResource
import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.data.source.remote.RemoteDataSource
import com.acalapatih.hafalin.core.data.source.remote.network.ApiResponse
import com.acalapatih.hafalin.core.data.source.remote.response.GetListAyatResponse
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanSuratModel
import com.acalapatih.hafalin.core.domain.repository.hafalanquran.HafalanSuratRepository
import kotlinx.coroutines.flow.Flow

class HafalanSuratRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
): HafalanSuratRepository {
    override fun getListAyat(nomorSurat: String): Flow<Resource<HafalanSuratModel>> =
        object : NetworkOnlyResource<HafalanSuratModel, GetListAyatResponse>() {
            override fun loadFromNetwork(data: GetListAyatResponse): Flow<HafalanSuratModel> =
                HafalanSuratModel.GetListAyat.mapResponseToModel(data)

            override suspend fun createCall(): Flow<ApiResponse<GetListAyatResponse>> =
                remoteDataSource.getListAyatSurat(nomorSurat)
        }.asFlow()
}