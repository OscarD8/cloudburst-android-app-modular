package com.example.ui.locations

data class LocationDetailUiState (
    val location : LocationUiModel? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val screenTitle: String = ""
)

