package com.acalapatih.oneayat.utils

import android.content.Context
import com.acalapatih.oneayat.core.data.source.local.preference.MainPreference
import com.acalapatih.oneayat.core.data.source.local.preference.MainPreferenceImpl
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val context: Context): Interceptor {
    private lateinit var mainPreferences: MainPreference

    private fun getMainPreferences(): MainPreference {
        if (!::mainPreferences.isInitialized) {
            mainPreferences = MainPreferenceImpl.getInstances(context)
        }

        return mainPreferences
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header("No-Authentication") == null) {
            if (getToken().isNotEmpty()) {
                val finalToken = "Bearer ${getToken()}"
                request = request.newBuilder()
                    .addHeader("Authorization", finalToken)
                    .addHeader("Ocp-Apim-Subscription-Key", Const.SPEECH_SERVICE_SUBSCRIPTION_KEY)
                    .addHeader("Content-Type", Const.SPEECH_SERVICE_CONTENT_TYPE)
                    .build()
            }
        }

        return chain.proceed(request)
    }

    private fun getToken(): String {
        return if (getMainPreferences().getToken() != null) {
            getMainPreferences().getToken()!!
        } else {
            ""
        }
    }
}