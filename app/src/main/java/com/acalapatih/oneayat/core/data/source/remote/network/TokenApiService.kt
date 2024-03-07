package com.acalapatih.oneayat.core.data.source.remote.network

import retrofit2.Call
import retrofit2.http.GET

interface TokenApiService {
    @GET("issuetoken")
    suspend fun getToken(): Call<String>
}