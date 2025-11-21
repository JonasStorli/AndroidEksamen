// File: data/CustomAnimeDao.kt
package com.example.andorideksamen.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CustomAnimeDao {

    @Query("SELECT * FROM custom_anime ORDER BY id DESC")
    suspend fun getAll(): List<CustomAnime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anime: CustomAnime)

    @Query("SELECT * FROM custom_anime WHERE isFavorite = 1 ORDER BY id DESC")
    suspend fun getFavoriteCustomAnimes(): List<CustomAnime>

    @Query("UPDATE custom_anime SET isFavorite = :isFavorite WHERE id = :animeId")
    suspend fun updateFavorite(animeId: Int, isFavorite: Boolean)
}
