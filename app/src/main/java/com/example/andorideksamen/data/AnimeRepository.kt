// File: data/AnimeRepository.kt
package com.example.andorideksamen.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimeRepository {

    // -------- Retrofit / HTTP client --------
    private val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .client(httpClient)
            // ✅ IMPORTANT: only /v4/, NOT /anime
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val animeService: AnimeService = retrofit.create(AnimeService::class.java)

    // -------- Room database --------
    private lateinit var appDatabase: AppDatabase

    private val animeDao: AnimeDao by lazy { appDatabase.animeDao() }
    private val customAnimeDao: CustomAnimeDao by lazy { appDatabase.customAnimeDao() }

    fun initialize(context: Context) {
        if (::appDatabase.isInitialized) return

        appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "anime_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // -------- Remote anime (from Jikan) --------

    private suspend fun refreshAnimeFromApi() {
        try {
            val response = animeService.getAnimeList()
            if (response.isSuccessful) {
                val dtoList = response.body()?.data.orEmpty()
                val entities = dtoList.map { it.toEntity() }

                animeDao.clearAnimes()
                animeDao.insertAnimes(entities)
            } else {
                Log.e(
                    "AnimeRepository",
                    "API error: ${response.code()} ${response.message()}"
                )
            }
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Network error while loading anime", e)
        }
    }

    suspend fun getAnimes(): List<Anime> {
        val local = animeDao.getAnimes()
        if (local.isNotEmpty()) {
            return local
        }

        // If DB empty → fetch from API, then read again
        refreshAnimeFromApi()
        return animeDao.getAnimes()
    }

    suspend fun getAnimeById(animeId: Int): Anime? {
        // Using cached data is enough for your details screen
        return animeDao.getAnimeById(animeId)
    }

    suspend fun getFavoriteAnimes(): List<Anime> {
        return animeDao.getFavoriteAnimes()
    }

    suspend fun toggleFavorite(anime: Anime) {
        animeDao.updateFavorite(anime.id, !anime.isFavorite)
    }

    // -------- Custom anime (user-created) --------

    suspend fun getCustomAnime(): List<CustomAnime> {
        return customAnimeDao.getAll()
    }

    suspend fun addCustomAnime(name: String, date: String, imageUrl: String) {
        val anime = CustomAnime(
            name = name,
            date = date,
            imageUrl = imageUrl
        )
        customAnimeDao.insert(anime)
    }

    suspend fun toggleCustomFavorite(anime: CustomAnime) {
        customAnimeDao.updateFavorite(anime.id, !anime.isFavorite)
    }
}
