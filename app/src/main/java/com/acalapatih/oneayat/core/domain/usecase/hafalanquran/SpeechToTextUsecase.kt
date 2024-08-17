package com.acalapatih.oneayat.core.domain.usecase.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.SpeechToTextModel
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface SpeechToTextUsecase {
    fun postSpeechToText(requestBody: RequestBody): Flow<Resource<SpeechToTextModel>>
}