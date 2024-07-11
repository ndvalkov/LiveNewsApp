package com.homeassignment.livenewsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.homeassignment.livenewsapp.ui.components.AppBottomNavigation
import com.homeassignment.livenewsapp.ui.components.AppRoute
import com.homeassignment.livenewsapp.ui.favorites.FavoritesScreen
import com.homeassignment.livenewsapp.ui.home.HomeScreen
import com.homeassignment.livenewsapp.ui.theme.LiveNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LiveNewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val allArticles by viewModel.allArticles.collectAsStateWithLifecycle()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    LaunchedEffect(Unit) {
                        viewModel.updateArticles()
                    }

                    val context = LocalContext.current
                    LaunchedEffect(uiState) {
                        if (uiState is UiState.Update) {
                            Toast.makeText(context, "Fetching latest articles ...", Toast.LENGTH_LONG).show()
                        }
                    }

                    when (val state = uiState) {
                        is UiState.Loading -> {
                            CircularProgressIndicator()
                        }
                        is UiState.Success -> {
                            AppBottomNavigation(navController = navController) { paddingValues ->
                                NavHost(
                                    navController = navController,
                                    startDestination = AppRoute.HOME.route,
                                    modifier = Modifier.padding(paddingValues)
                                ) {
                                    composable(AppRoute.HOME.route) { HomeScreen(allArticles) }
                                    composable(AppRoute.FAVORITE.route) { FavoritesScreen() }
                                    composable(AppRoute.SORT_BY.route) { }
                                    composable(AppRoute.SEARCH.route) { }
                                }
                            }
                        }
                        is UiState.Error -> {
                            Text(text = state.message)
                        }
                        UiState.Update -> {}
                    }
                }
            }
        }
    }
}




