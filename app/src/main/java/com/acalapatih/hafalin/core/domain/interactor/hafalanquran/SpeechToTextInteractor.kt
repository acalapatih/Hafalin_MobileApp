package com.acalapatih.hafalin.core.domain.interactor.hafalanquran

import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.domain.model.hafalanquran.SpeechToTextModel
import com.acalapatih.hafalin.core.domain.repository.hafalanquran.SpeechToTextRepository
import com.acalapatih.hafalin.core.domain.usecase.hafalanquran.SpeechToTextUsecase
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class SpeechToTextInteractor(
    private val repository: SpeechToTextRepository
): SpeechToTextUsecase {
    override fun postSpeechToText(
        requestBody: RequestBody
    ): Flow<Resource<SpeechToTextModel>> =
        repository.postSpeechToText(requestBody)
}