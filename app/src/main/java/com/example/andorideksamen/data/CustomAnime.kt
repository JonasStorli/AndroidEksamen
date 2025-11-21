
package com.example.andorideksamen.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// User-created anime stored locally
@Entity(tableName = "custom_anime")
data class CustomAnime(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val date: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
)
