// File: screens/anime_details/AnimeDetailsViewModel.kt
package com.example.andorideksamen.screens.anime_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andorideksamen.data.Anime
import com.example.andorideksamen.data.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _selectedAnime = MutableStateFlow<Anime?>(null)
    val selectedAnime = _selectedAnime.asStateFlow()

    fun setSelectedAnime(animeId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedAnime.value = AnimeRepository.getAnimeById(animeId)
            _loading.value = false
        }
    }
}
