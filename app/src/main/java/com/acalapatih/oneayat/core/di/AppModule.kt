package com.acalapatih.oneayat.core.di

import com.acalapatih.oneayat.core.data.repositoryImpl.bacaquran.BacaAyatRepositoryImpl
import com.acalapatih.oneayat.core.data.repositoryImpl.bacaquran.BacaJuzRepositoryImpl
import com.acalapatih.oneayat.core.data.repositoryImpl.bacaquran.ListSuratRepositoryImpl
import com.acalapatih.oneayat.core.data.repositoryImpl.bacaquran.BacaSuratRepositoryImpl
import com.acalapatih.oneayat.core.data.repositoryImpl.hafalanquran.*
import com.acalapatih.oneayat.core.data.source.remote.RemoteDataSource
import com.acalapatih.oneayat.core.data.source.remote.network.QuranApiService
import com.acalapatih.oneayat.core.data.source.remote.network.SpeechApiService
import com.acalapatih.oneayat.core.domain.interactor.bacaquran.BacaAyatInteractor
import com.acalapatih.oneayat.core.domain.interactor.bacaquran.BacaJuzInteractor
import com.acalapatih.oneayat.core.domain.interactor.bacaquran.ListSuratInteractor
import com.acalapatih.oneayat.core.domain.interactor.bacaquran.BacaSuratInteractor
import com.acalapatih.oneayat.core.domain.interactor.hafalanquran.*
import com.acalapatih.oneayat.core.domain.repository.bacaquran.BacaAyatRepository
import com.acalapatih.oneayat.core.domain.repository.bacaquran.BacaJuzRepository
import com.acalapatih.oneayat.core.domain.repository.bacaquran.ListSuratRepository
import com.acalapatih.oneayat.core.domain.repository.bacaquran.BacaSuratRepository
import com.acalapatih.oneayat.core.domain.repository.hafalanquran.*
import com.acalapatih.oneayat.core.domain.usecase.bacaquran.BacaAyatUsecase
import com.acalapatih.oneayat.core.domain.usecase.bacaquran.BacaJuzUsecase
import com.acalapatih.oneayat.core.domain.usecase.bacaquran.BacaQuranUsecase
import com.acalapatih.oneayat.core.domain.usecase.bacaquran.BacaSuratUsecase
import com.acalapatih.oneayat.core.domain.usecase.hafalanquran.*
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.BacaJuzViewModel
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.ListJuzViewModel
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.ListSuratViewModel
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.BacaSuratViewModel
import com.acalapatih.oneayat.ui.bookmark.viewmodel.BookmarkViewModel
import com.acalapatih.oneayat.ui.hafalanquran.viewmodel.HafalanAyatViewModel
import com.acalapatih.oneayat.ui.hafalanquran.viewmodel.HafalanQuranViewModel
import com.acalapatih.oneayat.ui.hafalanquran.viewmodel.HafalanSuratViewModel
import com.acalapatih.oneayat.ui.home.viewmodel.HomeViewModel
import com.acalapatih.oneayat.utils.Const
import com.acalapatih.oneayat.utils.Const.SPEECH_SERVICE_SUBSCRIPTION_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<QuranApiService> {
        Retrofit.Builder()
            .baseUrl(Const.QURAN_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuranApiService::class.java)
    }

    single<SpeechApiService> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val finalToken = "Bearer eyJhbGciOiJFUzI1NiIsImtpZCI6ImtleTEiLCJ0eXAiOiJKV1QifQ.eyJyZWdpb24iOiJzb3V0aGVhc3Rhc2lhIiwic3Vic2NyaXB0aW9uLWlkIjoiNmI4ZDhmNGY1ZGU4NGRiM2FkNzdlYzE1MWEzOGExYjkiLCJwcm9kdWN0LWlkIjoiU3BlZWNoU2VydmljZXMuUzAiLCJjb2duaXRpdmUtc2VydmljZXMtZW5kcG9pbnQiOiJodHRwczovL2FwaS5jb2duaXRpdmUubWljcm9zb2Z0LmNvbS9pbnRlcm5hbC92MS4wLyIsImF6dXJlLXJlc291cmNlLWlkIjoiL3N1YnNjcmlwdGlvbnMvMDIwMDc1NzktN2YxNC00NmZmLThhOTItMmJlZTUxY2MxOTdkL3Jlc291cmNlR3JvdXBzL0F6dXJlU3BlZWNoL3Byb3ZpZGVycy9NaWNyb3NvZnQuQ29nbml0aXZlU2VydmljZXMvYWNjb3VudHMvT25lQXlhdFNUVCIsInNjb3BlIjoic3BlZWNoc2VydmljZXMiLCJhdWQiOiJ1cm46bXMuc3BlZWNoc2VydmljZXMuc291dGhlYXN0YXNpYSIsImV4cCI6MTcwMTgzNTAxNCwiaXNzIjoidXJuOm1zLmNvZ25pdGl2ZXNlcnZpY2VzIn0.5k2f-7qaAeAZ5YfCOD00lq9v90JfT-RmV8MnTqLX9ySwi09l58hV88EI3t0-dgZYkwLdJEd6FozybAzsz-2n6g"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", finalToken)
                    .addHeader("Ocp-Apim-Subscription-Key", SPEECH_SERVICE_SUBSCRIPTION_KEY)
                    .addHeader("Content-type", "audio/wav; codecs=audio/pcm; samplerate=16000")
                    .build()
                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl(Const.SPEECH_SERVICE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SpeechApiService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get(), get()) }
    single<ListSuratRepository> { ListSuratRepositoryImpl(get()) }
    single<BacaSuratRepository> { BacaSuratRepositoryImpl(get()) }
    single<BacaJuzRepository> { BacaJuzRepositoryImpl(get()) }
    single<BacaAyatRepository> { BacaAyatRepositoryImpl(get()) }
    single<HafalanQuranRepository> { HafalanQuranRepositoryImpl(get()) }
    single<HafalanSuratRepository> { HafalanSuratRepositoryImpl(get()) }
    single<HafalanAyatRepository> { HafalanAyatRepositoryImpl(get()) }
    single<SpeechToTextRepository> { SpeechToTextRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory<BacaQuranUsecase> { ListSuratInteractor(get()) }
    factory<BacaSuratUsecase> { BacaSuratInteractor(get()) }
    factory<BacaJuzUsecase> { BacaJuzInteractor(get()) }
    factory<BacaAyatUsecase> { BacaAyatInteractor(get()) }
    factory<HafalanQuranUsecase> { HafalanQuranInteractor(get()) }
    factory<HafalanSuratUseCase> { HafalanSuratInteractor(get()) }
    single<HafalanAyatUsecase> { HafalanAyatInteractor(get()) }
    single<SpeechToTextUsecase> { SpeechToTextInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ListSuratViewModel(get()) }
    viewModel { ListJuzViewModel(get()) }
    viewModel { BacaSuratViewModel(get(), get(), get()) }
    viewModel { BacaJuzViewModel(get(), get(), get()) }
    viewModel { HafalanQuranViewModel(get(), get()) }
    viewModel { HafalanSuratViewModel(get(), get()) }
    viewModel { HafalanAyatViewModel(get(), get(), get()) }
    viewModel { BookmarkViewModel(get()) }
}