package com.example.cloudburst.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {

        }

        composable(Screen.LocationsList.route) {
            // The ViewModel for this screen will automatically get the "category" argument
            // via the SavedStateHandler

        }

        composable("locations/{category}"
        ) { backStackEntry ->
            LocationsList
        }
    }
}