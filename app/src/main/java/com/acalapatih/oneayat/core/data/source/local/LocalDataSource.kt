package com.acalapatih.oneayat.core.data.source.local

import com.acalapatih.oneayat.core.data.source.local.preference.MainPreference

class LocalDataSource(
    private val preference: MainPreference
) {
    fun getToken(): String {
        return preference.getToken() ?: ""
    }

    fun setToken(token: String) {
        preference.setToken(token)
    }

    fun clearToken() {
        preference.clearToken()
    }
}