package com.example.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.domain.model.LocationCategory

/*
    Defines the UI elements to navigation that the user can interact with.
    These are purely for displaying within the navigation components.
    Each holds a route used in connection with the NavHost.
 */

/**
 * NavigationItems - UI elements to be displayed in the navigation component.
 * @param route The route, similar to a URL, handled by the NavHost.
 * @param locationCategory Required in some NavItems to retrieve a list of locations by category.
 * @param icon The icon to be displayed in the navigation component.
 * @param labelRes The human readable label to be displayed in the navigation component.
 */
data class NavigationItem(
    val route: String,
    val locationCategory: LocationCategory?,
    @DrawableRes val icon: Int,
    @StringRes val labelRes: Int
)

private val allNavigationItems = listOf(
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

/**
 * Expanded devices display a navigation drawer. There is no need for a Categories screen
 * navigation element due to each category being displayed directly in the drawer.
 */
val expandedNavList = allNavigationItems.filter { it.route != Screen.Categories.route }

/**
 * Medium and Compact devices display a navigation rail or bar respectively. A single
 * Categories screen and navigable element are used to replace listing out each
 * category as a unique navigation destination in the navigation component.
 */
val minimisedNavList = allNavigationItems.filter { it.locationCategory == null }

/**
 * Map to retrieve a NavigationItem (UI element) by its route.
 */
val navigationItemMap: Map<String, NavigationItem> = allNavigationItems.associateBy { it.route }



