package com.example.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.core.navigation.R
import com.example.domain.model.LocationCategory

data class NavigationItem(
    val route: String,
    val locationCategory: LocationCategory?,
    @DrawableRes val icon: Int
)

val allNavigationItems = listOf(
    NavigationItem(
        route = Screen.Home.route,
        icon = R.drawable.ic_home,
        locationCategory = null
    ),
    NavigationItem(
        route = Screen.Categories.route,
        icon = R.drawable.ic_categories,
        locationCategory = null
    ),
    NavigationItem(
        route = Screen.Favourites.route,
        icon = R.drawable.ic_favourites,
        locationCategory = null
    ),
    NavigationItem(
        route = Screen.Restaurants.route,
        icon = R.drawable.ic_restaurant_placeholder_2,
        locationCategory = LocationCategory.RESTAURANT
    ),
    NavigationItem(
        route = Screen.Cafes.route,
        icon = R.drawable.ic_cafe_placeholder_1,
        locationCategory = LocationCategory.CAFE
    ),
    NavigationItem(
        route = Screen.Parks.route,
        icon = R.drawable.ic_park_placeholder_3,
        locationCategory = LocationCategory.PARK
    ),
    NavigationItem(
        route = Screen.Temples.route,
        icon = R.drawable.ic_temple_placeholder_2,
        locationCategory = LocationCategory.TEMPLE
    ),
    NavigationItem(
        route = Screen.Printers.route,
        icon = R.drawable.ic_mycelium_printer_placeholder_3,
        locationCategory = LocationCategory.MYCELIUM_PRINTER
    )
)

val expandedNavList = allNavigationItems.filter { it.route != Screen.Categories.route }
val minimisedNavList = allNavigationItems.filter { it.locationCategory == null }



