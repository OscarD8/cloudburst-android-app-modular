package com.example.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.domain.model.LocationCategory

/*
    Defines the visual elements to navigation that the user can interact with.
 */
data class NavigationItem(
    val route: String,
    val locationCategory: LocationCategory?,
    @DrawableRes val icon: Int,
    @StringRes val labelRes: Int
)

val allNavigationItems = listOf(
    NavigationItem(
        route = Screen.Home.route,
        icon = R.drawable.ic_home,
        locationCategory = null,
        labelRes = R.string.home_header
    ),
    NavigationItem(
        route = Screen.Categories.route,
        icon = R.drawable.ic_categories,
        locationCategory = null,
        labelRes = R.string.categories_header
    ),
    NavigationItem(
        route = Screen.Favourites.route,
        icon = R.drawable.ic_favourites,
        locationCategory = null,
        labelRes = R.string.favourites_header
    ),

    // Destinations for specific categories
    NavigationItem(
        route = Screen.LocationsList.createRoute(LocationCategory.RESTAURANTS.name),
        icon = R.drawable.ic_restaurant_placeholder_2,
        locationCategory = LocationCategory.RESTAURANTS,
        labelRes = R.string.restaurant_header
    ),
    NavigationItem(
        route = Screen.LocationsList.createRoute(LocationCategory.CAFES.name),
        icon = R.drawable.ic_cafe_placeholder_1,
        locationCategory = LocationCategory.CAFES,
        labelRes = R.string.cafe_header
    ),
    NavigationItem(
        route = Screen.LocationsList.createRoute(LocationCategory.PARKS.name),
        icon = R.drawable.ic_park_placeholder_3,
        locationCategory = LocationCategory.PARKS,
        labelRes = R.string.park_header
    ),
    NavigationItem(
        route = Screen.LocationsList.createRoute(LocationCategory.TEMPLES.name),
        icon = R.drawable.ic_temple_placeholder_2,
        locationCategory = LocationCategory.TEMPLES,
        labelRes = R.string.temple_header
    ),
    NavigationItem(
        route = Screen.LocationsList.createRoute(LocationCategory.PRINTERS.name),
        icon = R.drawable.ic_mycelium_printer_placeholder_3,
        locationCategory = LocationCategory.PRINTERS,
        labelRes = R.string.mycelium_printer_header
    )
)

val expandedNavList = allNavigationItems.filter { it.route != Screen.Categories.route }
val minimisedNavList = allNavigationItems.filter { it.locationCategory == null }

val navigationItemMap: Map<String, NavigationItem> = allNavigationItems.associateBy { it.route }



