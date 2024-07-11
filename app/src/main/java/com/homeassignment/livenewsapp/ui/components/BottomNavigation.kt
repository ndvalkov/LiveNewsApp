package com.homeassignment.livenewsapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)

enum class AppRoute(val route: String) {
    HOME("home"),
    FAVORITE("favorite"),
    SORT_BY("sort_by"),
    SEARCH("search")
}

val bottomNavItems = listOf(
    BottomNavItem(
        name = "Home",
        route = AppRoute.HOME.route,
        icon = Icons.Outlined.Home,
    ),
    BottomNavItem(
        name = "Favorite",
        route = AppRoute.FAVORITE.route,
        icon = Icons.Outlined.FavoriteBorder,
    ),
    BottomNavItem(
        name = "Sort By",
        route = AppRoute.SORT_BY.route,
        icon = Icons.Outlined.MoreVert,
    ),
    BottomNavItem(
        name = "Search",
        route = AppRoute.SEARCH.route,
        icon = Icons.Outlined.Search,
    )
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            AnimatedVisibility(true) {
                NavigationBar(
                    Modifier.clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)),
                    tonalElevation = 8.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = item.route.lowercase() ==
                                backStackEntry.value?.destination?.route?.lowercase()

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            label = {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "${item.name} Icon",
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            },
                            colors = NavigationBarItemDefaults
                                .colors(
                                    indicatorColor = MaterialTheme.colorScheme.surface
                                )
                        )
                    }
                }
            }
        },
        content = content
    )
}