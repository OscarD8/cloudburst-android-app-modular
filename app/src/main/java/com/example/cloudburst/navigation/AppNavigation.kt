package com.example.cloudburst.navigation

import androidx.annotation.StringRes
import com.example.cloudburst.R

sealed class Screen(val route: String, @StringRes val titleRes: Int) {

    // Screens with static titles
    data object Home : Screen("home", R.string.screen_title_home)
    data object Categories : Screen("categories", R.string.screen_title_categories)
    data object Favourites : Screen("favourites", R.string.screen_title_favourites)

    // For screens where the title might be dynamic, this can serve as a default title.
    data object LocationsList : Screen("locations/{category}", R.string.screen_title_locations_list) {
        fun createRoute(category: String) = "locations/$category"
    }

    // A default title for the detail screen. The actual title (e.g., the location's name)
    // would be handled by the ViewModel and hoisted up.
    data object LocationDetail : Screen("locationDetail/{locationId}", R.string.screen_title_location_detail) {
        fun createRoute(locationId: String) = "locationDetail/$locationId"
    }
}