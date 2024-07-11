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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.homeassignment.livenewsapp.ui.components.AppBottomNavigation
import com.homeassignment.livenewsapp.ui.components.AppRoute
import com.homeassignment.livenewsapp.ui.favorites.FavoritesScreen
import com.homeassignment.livenewsapp.ui.home.HomeScreen
import com.homeassignment.livenewsapp.ui.search.SearchScreen
import com.homeassignment.livenewsapp.ui.sort.SortScreen
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

                    val articles = viewModel.articles.collectAsLazyPagingItems()
                    // val allArticles by viewModel.allArticles.collectAsStateWithLifecycle()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    LaunchedEffect(Unit) {
                        viewModel.updateArticles()
                    }

                    val context = LocalContext.current
                    LaunchedEffect(uiState) {
                        if (uiState is UiState.Update) {
                            Toast.makeText(
                                context,
                                "Fetching latest articles ...",
                                Toast.LENGTH_LONG
                            ).show()
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
                                    composable(AppRoute.HOME.route) { HomeScreen(articles) }
                                    composable(AppRoute.FAVORITE.route) { FavoritesScreen() }
                                    composable(AppRoute.SORT_BY.route) {
                                        SortScreen(
                                            onDismiss = {
                                                navigateToHome(navController)
                                            },
                                            onSort = { sortAction ->

                                            }
                                        )
                                    }
                                    composable(AppRoute.SEARCH.route) {
                                        SearchScreen(
                                            onDismiss = {
                                                navigateToHome(navController)
                                            },
                                            onSearch = { searchAction, query ->

                                            }
                                        )
                                    }
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

    private fun navigateToHome(navController: NavHostController) {
        navController.navigate(AppRoute.HOME.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}




