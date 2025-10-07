package com.example.cloudburst.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
    object Favourites : Screen("favourites")

    // For when the screen needs a category argument
    object LocationsList : Screen("locations/{category}") {
        fun createRoute(category: String) = "locations/$category"
    }

    // For a screen that needs a location ID
    object LocationDetail : Screen("locationDetail/{locationId}") {
        fun createRoute(locationId: String) = "locationDetail/$locationId"
    }
}