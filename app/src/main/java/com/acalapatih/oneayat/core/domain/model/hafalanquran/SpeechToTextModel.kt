package com.acalapatih.oneayat.core.domain.model.hafalanquran

import com.acalapatih.oneayat.core.data.source.remote.response.PostSpeechToTextResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SpeechToTextModel(
    val hasilText: String
) {
    companion object {
        fun mapResponseToModel(response: PostSpeechToTextResponse): Flow<SpeechToTextModel> {
            return flowOf(
                SpeechToTextModel(
                    hasilText = response.displayText ?: ""
                )
            )
        }
    }
}
