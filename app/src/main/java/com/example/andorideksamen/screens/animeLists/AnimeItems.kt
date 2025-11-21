// File: screens/animeLists/AnimeItems.kt
package com.example.andorideksamen.screens.animeLists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.andorideksamen.data.Anime
import com.example.andorideksamen.data.CustomAnime

// -------- Remote anime card (heart icon -> red) --------

@Composable
fun AnimeItem(
    anime: Anime,
    onClick: () -> Unit,
    onToggleFavorite: (Anime) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF111827), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AsyncImage(
            model = anime.imageUrl,
            contentDescription = anime.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = anime.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = anime.bio,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9CA3AF),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(onClick = { onToggleFavorite(anime) }) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite",
                tint = if (anime.isFavorite) Color.Red else Color(0xFF6B7280)
            )
        }
    }
}

// -------- Custom anime card (save icon -> yellow) --------

@Composable
fun CustomAnimeItem(
    anime: CustomAnime,
    onClick: () -> Unit = {},
    onToggleFavorite: (CustomAnime) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF111827), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AsyncImage(
            model = anime.imageUrl,
            contentDescription = anime.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = anime.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = anime.date,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9CA3AF),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(onClick = { onToggleFavorite(anime) }) {
            Icon(
                imageVector = Icons.Filled.Bookmark,
                contentDescription = "Save",
                tint = if (anime.isFavorite) Color(0xFFFFC107) else Color(0xFF6B7280)
            )
        }
    }
}
