// File: data/AnimeService.kt
package com.example.andorideksamen.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {

    // GET https://api.jikan.moe/v4/anime
    @GET("anime")
    suspend fun getAnimeList(): Response<JikanAnimeListResponse>

    // GET https://api.jikan.moe/v4/anime/{id}
    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): Response<JikanAnimeDto>
}
