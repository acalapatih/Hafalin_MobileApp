package com.acalapatih.oneayat.core.data.source.local.preference

interface MainPreference {
    fun getToken(): String?
    fun setToken(token: String)
    fun clearToken()
}