// File: navigations/animeDestinations.kt
package com.example.andorideksamen.navigations

import kotlinx.serialization.Serializable

@Serializable
object AnimeList

@Serializable
data class AnimeDetails(
    val animeId: Int
)

@Serializable
object FavoritesList

@Serializable
object CustomAnimeList
