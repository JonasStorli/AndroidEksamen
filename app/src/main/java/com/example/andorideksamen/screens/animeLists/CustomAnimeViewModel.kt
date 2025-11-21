// File: screens/custom/CustomAnimeViewModel.kt
package com.example.andorideksamen.screens.custom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andorideksamen.data.AnimeRepository
import com.example.andorideksamen.data.CustomAnime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomAnimeViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _customAnimes = MutableStateFlow<List<CustomAnime>>(emptyList())
    val customAnimes = _customAnimes.asStateFlow()

    init {
        loadCustomAnimes()
    }

    fun loadCustomAnimes() {
        viewModelScope.launch {
            _loading.value = true
            _customAnimes.value = AnimeRepository.getCustomAnime()
            _loading.value = false
        }
    }

    fun addCustomAnime(title: String, date: String, imageUrl: String) {
        viewModelScope.launch {
            _loading.value = true
            AnimeRepository.addCustomAnime(title, date, imageUrl)
            _customAnimes.value = AnimeRepository.getCustomAnime()
            _loading.value = false
        }
    }

    fun toggleFavorite(item: CustomAnime) {
        viewModelScope.launch {
            _loading.value = true
            AnimeRepository.toggleCustomFavorite(item)
            _customAnimes.value = AnimeRepository.getCustomAnime()
            _loading.value = false
        }
    }
}
