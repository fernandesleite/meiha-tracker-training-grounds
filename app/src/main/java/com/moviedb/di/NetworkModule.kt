package com.moviedb.di

import com.moviedb.network.ApiInterceptor
import com.moviedb.network.TMDbApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): TMDbApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(TMDbApiService::class.java)
    }

    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .build()
    }

    single { provideMoshi() }

    single { provideOkHttpClient() }

    single { provideRetrofit(moshi = get(), client = get()) }
}