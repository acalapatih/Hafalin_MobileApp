package com.acalapatih.oneayat.core.domain.model.hafalanquran

import com.acalapatih.oneayat.core.data.source.remote.response.PostSpeechToTextResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call

data class RequestTokenModel(
    val token: String
) {
    companion object {
        fun mapResponseToModel(response: Call<String>): Flow<RequestTokenModel> {
            return flowOf(
                RequestTokenModel(
                    token = response.toString()
                )
            )
        }
    }
}
