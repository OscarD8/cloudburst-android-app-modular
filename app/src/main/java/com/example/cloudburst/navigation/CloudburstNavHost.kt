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

@Composable
fun CloudburstNavHost(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            CloudburstHomeScreen(windowSize, modifier = Modifier.fillMaxSize())
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                windowSize = windowSize,
                onCategorySelected = { category -> // list item for category passes that NavItem's route up
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
//            LocationsListScreen(
//                windowSize = windowSize,
//                onLocationClicked = { locationId ->
//                    navController.navigate(Screen.LocationDetail.createRoute(locationId))
//                },
//                onClickToExpand = {}
//            )
        }
    }
}