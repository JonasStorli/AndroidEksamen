// File: screens/animeLists/AnimeListScreen.kt
package com.example.andorideksamen.screens.animeLists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel,
    onAnimeClick: (Int) -> Unit,
    onFavoritesClick: () -> Unit,
    onCustomClick: () -> Unit
) {
    val loading by viewModel.loading.collectAsState()
    val animeList by viewModel.animeList.collectAsState()

    var searchText by rememberSaveable { mutableStateOf("") }

    val listToShow = if (searchText.isBlank()) {
        animeList
    } else {
        animeList.filter {
            it.name.contains(searchText, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020617))
            .padding(16.dp)
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Anime Explorer",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Row {
                TextButton(onClick = onFavoritesClick) {
                    Text(text = "Favorites", color = Color(0xFF93C5FD))
                }
                TextButton(onClick = onCustomClick) {
                    Text(text = "Custom", color = Color(0xFF93C5FD))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF9CA3AF)
                )
            },
            placeholder = {
                Text(text = "Search anime...", color = Color(0xFF6B7280))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color(0xFF93C5FD))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Loading anime…", color = Color.White)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listToShow) { anime ->
                    AnimeItem(
                        anime = anime,
                        onClick = { onAnimeClick(anime.id) },
                        onToggleFavorite = { item ->
                            viewModel.toggleFavorite(item)
                        }
                    )
                }
            }
        }
    }
}
