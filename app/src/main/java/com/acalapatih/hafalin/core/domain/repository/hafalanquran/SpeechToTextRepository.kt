package com.acalapatih.hafalin.core.domain.repository.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.SpeechToTextModel
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface SpeechToTextRepository {
    fun postSpeechToText(requestBody: RequestBody): Flow<Resource<SpeechToTextModel>>
}