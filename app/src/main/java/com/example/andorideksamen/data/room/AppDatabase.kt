// File: data/AppDatabase.kt
package com.example.andorideksamen.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Anime::class, CustomAnime::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun customAnimeDao(): CustomAnimeDao
}
