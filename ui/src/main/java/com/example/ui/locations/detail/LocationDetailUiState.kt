package com.example.ui.locations.detail

import com.example.ui.locations.LocationUiModel

/**
 * Represents the state of the location detail screen.
 * @property location The location being displayed.
 * @property isLoading Whether the screen is currently loading.
 * @property error The error message, if any.
 */
data class LocationDetailUiState (
    val location : LocationUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

