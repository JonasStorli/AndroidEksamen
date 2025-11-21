// File: data/AnimeDao.kt
package com.example.andorideksamen.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY id ASC")
    suspend fun getAnimes(): List<Anime>

    @Query("SELECT * FROM anime WHERE id = :animeId LIMIT 1")
    suspend fun getAnimeById(animeId: Int): Anime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimes(animes: List<Anime>)

    @Query("DELETE FROM anime")
    suspend fun clearAnimes()

    @Query("SELECT * FROM anime WHERE isFavorite = 1 ORDER BY id DESC")
    suspend fun getFavoriteAnimes(): List<Anime>

    @Query("UPDATE anime SET isFavorite = :isFavorite WHERE id = :animeId")
    suspend fun updateFavorite(animeId: Int, isFavorite: Boolean)
}
