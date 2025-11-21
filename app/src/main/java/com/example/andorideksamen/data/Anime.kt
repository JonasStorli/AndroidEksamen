// File: data/Anime.kt
package com.example.andorideksamen.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// Remote anime from API, cached in Room
@Entity(tableName = "anime")
data class Anime(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val bio: String,
    val isFavorite: Boolean = false
)

// -------------------- JIKAN DTOs --------------------

data class JikanAnimeListResponse(
    @SerializedName("data")
    val data: List<JikanAnimeDto>
)

data class JikanAnimeDto(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("images")
    val images: JikanImages
)

data class JikanImages(
    @SerializedName("jpg")
    val jpg: JikanImageJpg
)

data class JikanImageJpg(
    @SerializedName("image_url")
    val imageUrl: String
)

// Mapping from Jikan DTO to Room entity
fun JikanAnimeDto.toEntity(): Anime =
    Anime(
        id = malId,
        name = title,
        imageUrl = images.jpg.imageUrl,
        bio = synopsis ?: "",
        isFavorite = false
    )
