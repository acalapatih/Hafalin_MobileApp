package com.acalapatih.oneayat.core.domain.interactor.hafalanquran

import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.SpeechToTextModel
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.SpeechToTextRepository
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.SpeechToTextUsecase
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class SpeechToTextInteractor(
    private val repository: SpeechToTextRepository
): SpeechToTextUsecase {
    override fun getToken(): String =
        repository.getToken()

    override fun postSpeechToText(
        requestBody: RequestBody
    ): Flow<Resource<SpeechToTextModel>> =
        repository.postSpeechToText(requestBody)
}