package com.acalapatih.hafalin.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PostSpeechToTextResponse(

	@field:SerializedName("RecognitionStatus")
	val recognitionStatus: String? = null,

	@field:SerializedName("DisplayText")
	val displayText: String? = null,

	@field:SerializedName("Duration")
	val duration: Int? = null,

	@field:SerializedName("Offset")
	val offset: Int? = null
)
