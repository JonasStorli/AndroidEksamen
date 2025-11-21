// File: screens/animeLists/AnimeListViewModel.kt
package com.example.andorideksamen.screens.animeLists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andorideksamen.data.Anime
import com.example.andorideksamen.data.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeListViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList = _animeList.asStateFlow()

    init {
        loadAnimes()
    }

    fun loadAnimes() {
        viewModelScope.launch {
            _loading.value = true
            _animeList.value = AnimeRepository.getAnimes()
            _loading.value = false
        }
    }

    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            AnimeRepository.toggleFavorite(anime)
            _animeList.value = AnimeRepository.getAnimes()
        }
    }
}
