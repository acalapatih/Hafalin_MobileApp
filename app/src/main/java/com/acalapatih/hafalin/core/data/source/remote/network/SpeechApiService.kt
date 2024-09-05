package com.acalapatih.hafalin.core.data.source.remote.network

import com.acalapatih.hafalin.core.data.source.remote.response.PostSpeechToTextResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface SpeechApiService {
    @POST("speech/recognition/conversation/cognitiveservices/v1?language=ar-SA")
    suspend fun postSpeechToText(
        @Body requestBody: RequestBody
    ): PostSpeechToTextResponse
}