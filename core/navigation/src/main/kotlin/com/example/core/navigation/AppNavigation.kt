package com.example.core.navigation

import androidx.annotation.StringRes

sealed class Screen(val route: String) {

    // Screens with static titles
    data object Home : Screen("home")
    data object Categories : Screen("categories")
    data object Favourites : Screen("favourites")
    data object Restaurants : Screen("restaurants")
    data object Cafes : Screen("cafes")
    data object Parks : Screen("parks")
    data object Temples : Screen("temples")
    data object Printers : Screen("printers")

    // For screens where the title might be dynamic, this can serve as a default title.
    data object LocationsList : Screen("locations/{category}", ) {
        fun createRoute(category: String) = "locations/$category"
    }

    // A default title for the detail screen. The actual title (e.g., the location's name)
    // would be handled by the ViewModel and hoisted up.
    data object LocationDetail : Screen("locationDetail/{locationId}") {
        fun createRoute(locationId: Long) = "locationDetail/$locationId"
    }
}