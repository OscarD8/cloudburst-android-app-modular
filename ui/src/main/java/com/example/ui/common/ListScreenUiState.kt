package com.example.ui.common

/**
 * UI state for the a screen displaying lists.
 *
 * @param isLoading Whether the screen is currently loading data.
 * @param items The list of items to display.
 */
data class ListScreenUiState<T> (
    val isLoading: Boolean = false,
    val items: List<T> = emptyList(),
    val error: String? = null
)