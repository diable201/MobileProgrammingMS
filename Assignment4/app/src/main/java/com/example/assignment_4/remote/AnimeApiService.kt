package com.example.assignment_4.remote

import com.example.assignment_4.model.AnimeDetailResponse
import com.example.assignment_4.model.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApiService {
    @GET("seasons/upcoming")
    suspend fun getUpcomingAnime(): AnimeResponse

    @GET("top/anime")
    suspend fun getTopAnime(): AnimeResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): AnimeDetailResponse
}
