package com.example.cloudburst.ui


/**
 * Defines enum constants for possible content types.
 * LIST_ONLY: Represents a content type where only the list view is available (Compact and Medium).
 * LIST_AND_DETAIL: Represents a content type where both the list view and detail view are available (Expanded).
 */
enum class CloudburstContentType{
    LIST_ONLY, LIST_AND_DETAIL
}

/**
 * Defines enum constants for possible navigation types.
 * NAVIGATION_RAIL: (Medium)
 * BOTTOM_NAVBAR: (Compact)
 * PERMANENT_NAVIGATION_DRAWER: (Expanded)
 */
enum class CloudburstNavigationType {
    NAVIGATION_RAIL, BOTTOM_NAVBAR, PERMANENT_NAVIGATION_DRAWER
}