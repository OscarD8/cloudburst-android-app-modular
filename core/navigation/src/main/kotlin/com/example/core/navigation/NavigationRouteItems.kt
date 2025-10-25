package com.example.core.navigation


/*
    Notes:
    Mapping all possible navigation routes/paths into one file for maintainability and
    to enable 'when' functionality for Screen types (compiler knows each possible instance
    of screen, sealed classes restrict inheritance).
    Data objects hold methods like toString() and equals() similar to data classes
    but singletons required as a route is a fixed concept, needing only one instance.
 */


/**
 * Represents the definitive map of all possible navigation destinations within the application.
 * This class serves as the **single source of truth** for navigation routes, ensuring maintainability
 * and type safety.
 *
 * @param route The stable, programmatic string identifier for this navigation destination.
 **/
sealed class Screen(val route: String) {

    // Screens with static titles
    data object Home : Screen("home")
    data object Categories : Screen("categories")
    data object Favourites : Screen("favourites")

    /*
        Notes:
        Screens with routes that depend on the arguments passed.
        Idea is to work specifically with strings as url type navigation methods
        instead of passing state objects around. Therefore, a string/url is
        required for each category or location, with a way of creating this
        dynamically at runtime and linking to the correct location category enum
        of a location model to return them as a list, or the correct id of a single location.
    */

    /**
     * Creates the specific navigation route for the locations list screen
     * by substituting the provided [category] name into the route template.
     *
     * @param category The name of the category to display (e.g., "CAFE", "RESTAURANT").
     * @return The fully formed route string (e.g., "locations/CAFE").
     */
    data object LocationsList : Screen("locations/{category}", ) {
        fun createRoute(category: String) = "locations/$category"
    }

    /**
     * Creates the specific navigation route for the location detail screen
     * by substituting the provided [locationId] into the route template.
     *
     * @param locationId The unique ID of the location to display.
     * @return The fully formed route string (e.g., "locationDetail/123").
     */
    data object LocationDetail : Screen("locationDetail/{locationId}") {
        fun createRoute(locationId: Long) = "locationDetail/$locationId"
    }
}
