package com.acalapatih.hafalin.core.data.source.remote

import com.acalapatih.hafalin.core.data.source.remote.network.ApiResponse
import com.acalapatih.hafalin.core.data.source.remote.network.QuranApiService
import com.acalapatih.hafalin.core.data.source.remote.network.SpeechApiService
import com.acalapatih.hafalin.core.data.source.remote.response.*
import com.acalapatih.hafalin.utils.setGeneralError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody

class RemoteDataSource(
    private val quranApiService: QuranApiService,
    private val speechApiService: SpeechApiService
) {
    suspend fun getListSurah(): Flow<ApiResponse<GetListSuratResponse>> {
        return flow {
            try {
                val response = quranApiService.getListSurat()

                if (response.code == 200) {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.setGeneralError()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListAyatSurat(nomorSurat: String): Flow<ApiResponse<GetListAyatResponse>> {
        return flow {
            try {
                val response = quranApiService.getListAyatSurat(nomorSurat)

                if (response.code == 200) {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.setGeneralError()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListAyatJuz(nomorJuz: String): Flow<ApiResponse<GetJuzResponse>> {
        return flow {
            try {
                val response = quranApiService.getListAyatJuz(nomorJuz)

                if (response.code == 200) {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.setGeneralError()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAyat(nomorSurat: String, nomorAyat: String): Flow<ApiResponse<GetAyatResponse>> {
        return flow {
            try {
                val response = quranApiService.getAyat(nomorSurat, nomorAyat)

                if (response.code == 200) {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.setGeneralError()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postSpeechToText(requestBody: RequestBody): Flow<ApiResponse<PostSpeechToTextResponse>> {
        return flow {
            try {
                val response = speechApiService.postSpeechToText(requestBody)

                if (response.recognitionStatus == "Success") {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.setGeneralError()))
            }
        }.flowOn(Dispatchers.IO)
    }
}