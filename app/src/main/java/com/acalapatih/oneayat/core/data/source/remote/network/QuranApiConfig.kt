package com.acalapatih.oneayat.core.data.source.remote.network

import com.acalapatih.oneayat.BuildConfig
import com.acalapatih.oneayat.utils.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuranApiConfig {
//    fun getQuranApiService(): QuranApiService {
//        val loggingInterceptor = if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        } else {
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//        }
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(Const.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//        return retrofit.create(QuranApiService::class.java)
//    }
}