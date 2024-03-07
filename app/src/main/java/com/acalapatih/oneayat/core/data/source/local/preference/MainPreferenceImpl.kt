package com.acalapatih.oneayat.core.data.source.local.preference

import android.content.Context
import android.content.SharedPreferences

class MainPreferenceImpl(
    private val sharedPreferences: SharedPreferences
): MainPreference {
    companion object {
        fun getInstances(context: Context): MainPreference {
            val pref = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
            return MainPreferenceImpl(pref)
        }

        private const val PREFERENCES_NAME = "ONNEAYAT_SHARED_PREFERENCES"
        private const val PREF_TOKEN_AUTH = "PREF_TOKEN_AUTH"
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(PREF_TOKEN_AUTH, "")
    }

    override fun setToken(token: String) {
        sharedPreferences.edit().putString(PREF_TOKEN_AUTH, token).apply()
    }

    override fun clearToken() {
        sharedPreferences.edit().remove(PREF_TOKEN_AUTH).apply()
    }
}