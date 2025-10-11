package com.example.core.navigation

import androidx.annotation.StringRes

/*
    Map of the entire application. Its only job is to be a single source of truth for
    all possible navigation routes by providing stable, type-safe, programmatic identifiers
    for navigation routes.
 */
sealed class Screen(val route: String) {

    // Screens with static titles
    data object Home : Screen("home")
    data object Categories : Screen("categories")
    data object Favourites : Screen("favourites")


    // For screens where the title might be dynamic, this can serve as a default title.
    data object LocationsList : Screen("locations/{category}", ) {
        fun createRoute(category: String) = "locations/$category"
    }
    // could return "locations/CAFE for example"

    // A default title for the detail screen. The actual title (e.g., the location's name)
    // would be handled by the ViewModel and hoisted up.
    data object LocationDetail : Screen("locationDetail/{locationId}") {
        fun createRoute(locationId: Long) = "locationDetail/$locationId"
    }
}