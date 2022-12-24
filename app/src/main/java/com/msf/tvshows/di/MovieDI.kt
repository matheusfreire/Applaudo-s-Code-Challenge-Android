package com.msf.tvshows.di

import com.msf.itunessearch.network.LoggingInterceptor
import com.msf.tvshows.BuildConfig
import com.msf.tvshows.core.CoroutinesContextProvider
import com.msf.tvshows.core.RequestWrapper
import com.msf.tvshows.core.RequestWrapperImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieDI {
    val module = module {
        single { CoroutinesContextProvider() }
        factory<RequestWrapper> { RequestWrapperImpl() }
        factory { LoggingInterceptor() }
        factory { provideOkHttpClient(interceptor = get()) }
        factory { provideRetrofit(okHttpClient = get()) }
    }
}

fun provideOkHttpClient(interceptor: LoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
