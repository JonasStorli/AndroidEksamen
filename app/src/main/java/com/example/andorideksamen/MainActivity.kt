// File: MainActivity.kt
package com.example.andorideksamen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.andorideksamen.data.AnimeRepository
import com.example.andorideksamen.navigations.AnimeDetails
import com.example.andorideksamen.navigations.AnimeList
import com.example.andorideksamen.navigations.CustomAnimeList
import com.example.andorideksamen.navigations.FavoritesList
import com.example.andorideksamen.screens.animeLists.AnimeListScreen
import com.example.andorideksamen.screens.animeLists.AnimeListViewModel
import com.example.andorideksamen.screens.animeLists.FavoritesListScreen
import com.example.andorideksamen.screens.animeLists.FavoritesListViewModel
import com.example.andorideksamen.screens.anime_details.AnimeDetailsScreen
import com.example.andorideksamen.screens.anime_details.AnimeDetailsViewModel
import com.example.andorideksamen.screens.custom.CustomAnimeScreen
import com.example.andorideksamen.screens.custom.CustomAnimeViewModel
import com.example.andorideksamen.ui.theme.AndoridEksamenTheme

class MainActivity : ComponentActivity() {

    private val animeListViewModel: AnimeListViewModel by viewModels()
    private val favoritesListViewModel: FavoritesListViewModel by viewModels()
    private val animeDetailsViewModel: AnimeDetailsViewModel by viewModels()
    private val customAnimeViewModel: CustomAnimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AnimeRepository.initialize(applicationContext)

        enableEdgeToEdge()
        setContent {
            AndoridEksamenTheme {
                val navController = rememberNavController()

                NavHost(
                    modifier = Modifier.systemBarsPadding(),
                    navController = navController,
                    startDestination = AnimeList
                ) {
                    composable<AnimeList> {
                        AnimeListScreen(
                            viewModel = animeListViewModel,
                            onAnimeClick = { id ->
                                navController.navigate(AnimeDetails(id))
                            },
                            onFavoritesClick = {
                                navController.navigate(FavoritesList)
                            },
                            onCustomClick = {
                                navController.navigate(CustomAnimeList)
                            }
                        )
                    }

                    composable<FavoritesList> {
                        FavoritesListScreen(
                            viewModel = favoritesListViewModel,
                            onAnimeClick = { id ->
                                navController.navigate(AnimeDetails(id))
                            },
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    composable<CustomAnimeList> {
                        CustomAnimeScreen(
                            viewModel = customAnimeViewModel,
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    composable<AnimeDetails> { backStackEntry ->
                        val args = backStackEntry.toRoute<AnimeDetails>()
                        animeDetailsViewModel.setSelectedAnime(args.animeId)

                        AnimeDetailsScreen(
                            viewModel = animeDetailsViewModel,
                            onBackButtonClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
