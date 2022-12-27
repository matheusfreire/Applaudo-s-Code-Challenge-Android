package com.msf.tvshows.di

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.msf.tvshows.BuildConfig
import com.msf.tvshows.network.LoggingInterceptor
import com.msf.tvshows.network.TvShowService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvShowNetworkDI {
    val modules = module {
        factory { LoggingInterceptor() }
        factory { provideOkHttpClient(interceptor = get()) }
        factory { provideRetrofit(okHttpClient = get()) }
        factory { providerServiceApi(retrofit = get()) }
        factory {
            provideImageLoader(
                context = androidContext(),
                okHttpClient = get()
            )
        }
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

fun provideImageLoader(
    context: Context,
    okHttpClient: OkHttpClient
): ImageLoader {
    return ImageLoader.Builder(context)
        .okHttpClient { okHttpClient }
        .components {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
}

fun providerServiceApi(retrofit: Retrofit): TvShowService = retrofit.create(TvShowService::class.java)
