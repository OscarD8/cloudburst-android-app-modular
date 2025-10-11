package com.example.cloudburst.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.navigation.Screen
import com.example.ui.categories.CategoriesScreen
import com.example.ui.home.CloudburstHomeScreen
import com.example.ui.locations.list.LocationsListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            CloudburstHomeScreen(windowSize, modifier = Modifier.fillMaxSize())
        }
        composable(Screen.Categories.route) {
            CategoriesScreen(
                windowSize = windowSize,
                onCategorySelected = { category ->
                    navController.navigate(Screen.LocationsList.createRoute(category.name))
                },
                modifier = Modifier.fillMaxSize()
            )
        }



        // One composable handles all location list screens
        composable(Screen.LocationsList.route) {
            // LocationsListScreen's viewmodel will use the "category" argument
            // from the route to fetch the correct data
            LocationsListScreen(
                onLocationClicked = { locationId ->
                    navController.navigate(Screen.LocationDetail.createRoute(locationId))
                },
                onClickToExpand = {}
            )
        }
    }
}