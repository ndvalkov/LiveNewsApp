package com.homeassignment.livenewsapp

import android.os.Bundle
import android.os.Parcelable
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.homeassignment.livenewsapp.data.db.Article
import com.homeassignment.livenewsapp.ui.components.AppBottomNavigation
import com.homeassignment.livenewsapp.ui.components.AppRoute
import com.homeassignment.livenewsapp.ui.components.ArticleDetail
import com.homeassignment.livenewsapp.ui.components.ArticleListItem
import com.homeassignment.livenewsapp.ui.favorites.FavoritesScreen
import com.homeassignment.livenewsapp.ui.home.HomeScreen
import com.homeassignment.livenewsapp.ui.theme.LiveNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LiveNewsAppTheme {
                val navController = rememberNavController()

                AppBottomNavigation(navController = navController) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = AppRoute.HOME.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(AppRoute.HOME.route) { HomeScreen() }
                        composable(AppRoute.FAVORITE.route) { FavoritesScreen() }
                        composable(AppRoute.SORT_BY.route) {  }
                        composable(AppRoute.SEARCH.route) {  }
                    }
                }
            }
        }
    }
}




