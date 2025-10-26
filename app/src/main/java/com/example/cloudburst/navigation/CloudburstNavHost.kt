package com.example.cloudburst.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.navigation.Screen
import com.example.ui.categories.CategoriesScreen
import com.example.ui.home.CloudburstHomeScreen
import com.example.ui.locations.list.LocationsListScreen

/**
 * Defines the routes for navigation.
 */
@Composable
fun CloudburstNavHost(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
    setTopBarTitle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            CloudburstHomeScreen(
                setTopBarTitle = setTopBarTitle,
                windowSize,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                windowSize = windowSize,
                setTopBarTitle = setTopBarTitle,
                onCategorySelected = { category ->
                    navController.navigate(Screen.LocationsList.createRoute(category.name)) // route passed in to make URL by category
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // One composable handles all location list screens
        composable(
            route = Screen.LocationsList.route,
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                }
            )
        ) {
            // LocationsListScreen's viewmodel will use the "category" argument
            // from the route to fetch the correct data
            LocationsListScreen(
                setTopBarTitle = setTopBarTitle,
                windowSize = windowSize,
                onExploreClicked = { locationId ->
                    navController.navigate(Screen.LocationDetail.createRoute(locationId))
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}