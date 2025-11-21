// File: screens/animeLists/FavoritesListViewModel.kt
package com.example.andorideksamen.screens.animeLists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andorideksamen.data.Anime
import com.example.andorideksamen.data.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesListViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _favorites = MutableStateFlow<List<Anime>>(emptyList())
    val favorites = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _loading.value = true
            _favorites.value = AnimeRepository.getFavoriteAnimes()
            _loading.value = false
        }
    }

    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            AnimeRepository.toggleFavorite(anime)
            loadFavorites()
        }
    }
}
