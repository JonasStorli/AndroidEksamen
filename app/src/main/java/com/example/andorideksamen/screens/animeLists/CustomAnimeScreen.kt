// File: screens/custom/CustomAnimeScreen.kt
package com.example.andorideksamen.screens.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.andorideksamen.data.CustomAnime
import com.example.andorideksamen.screens.animeLists.CustomAnimeItem

@Composable
fun CustomAnimeScreen(
    viewModel: CustomAnimeViewModel,
    onBackClick: () -> Unit
) {
    val loading by viewModel.loading.collectAsState()
    val customAnimes by viewModel.customAnimes.collectAsState()

    var searchText by rememberSaveable { mutableStateOf("") }

    var name by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var selectedImageUrl by rememberSaveable { mutableStateOf("") }
    var showImagePicker by rememberSaveable { mutableStateOf(false) }

    val imageOptions = listOf(
        // sample images – you can swap these with your own
        "https://cdn.myanimelist.net/images/anime/5/17407.jpg",
        "https://cdn.myanimelist.net/images/anime/10/47347.jpg",
        "https://cdn.myanimelist.net/images/anime/13/62147.jpg"
    )

    val listToShow = if (searchText.isBlank()) {
        customAnimes
    } else {
        customAnimes.filter {
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Custom Anime",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search
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
                Text(text = "Search custom anime…", color = Color(0xFF6B7280))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111827), MaterialTheme.shapes.medium)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Create your own anime",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Name") }
            )

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Date (optional)") }
            )

            // Black card with + icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color.Black, MaterialTheme.shapes.medium)
                    .clickable { showImagePicker = true },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUrl.isBlank()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add image",
                            tint = Color.White
                        )
                        Text(
                            text = "Tap to choose image",
                            color = Color.White
                        )
                    }
                } else {
                    AsyncImage(
                        model = selectedImageUrl,
                        contentDescription = "Selected image",
                        modifier = Modifier
                            .height(140.dp)
                            .fillMaxWidth()
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.addCustomAnime(name, date, selectedImageUrl)
                    name = ""
                    date = ""
                    selectedImageUrl = ""
                },
                enabled = name.isNotBlank() && selectedImageUrl.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save custom anime")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color(0xFF93C5FD))
                Text(text = "Loading your anime…", color = Color.White)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listToShow) { anime ->
                    CustomAnimeItem(
                        anime = anime,
                        onToggleFavorite = { item ->
                            viewModel.toggleFavorite(item)
                        }
                    )
                }
            }
        }
    }

    // Image picker dialog
    if (showImagePicker) {
        AlertDialog(
            onDismissRequest = { showImagePicker = false },
            title = { Text(text = "Choose image") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    imageOptions.forEach { url ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedImageUrl = url
                                    showImagePicker = false
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                AsyncImage(
                                    model = url,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                )
                                Text(
                                    text = url,
                                    style = MaterialTheme.typography.bodySmall,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showImagePicker = false }) {
                    Text(text = "Close")
                }
            }
        )
    }
}
