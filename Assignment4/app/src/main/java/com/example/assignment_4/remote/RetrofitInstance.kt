package com.example.assignment_4.remote

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object RetrofitClient {
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    private fun provideCache(context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, cacheSize)
    }

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val maxAge = 60
            response.newBuilder().header("Cache-Control", "public, max-age=$maxAge").build()
        }
    }

    private fun provideOfflineCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtils.hasNetwork(context)) {
                val maxStale = 60 * 60 * 24 * 7
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale").build()
            }
            chain.proceed(request)
        }
    }

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().cache(provideCache(context))
            .addInterceptor(provideOfflineCacheInterceptor(context))
            .addNetworkInterceptor(provideCacheInterceptor()).build()
    }

    private fun getRetrofit(context: Context): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(provideOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getApiService(context: Context): AnimeApiService {
        return getRetrofit(context).create(AnimeApiService::class.java)
    }
}
