package com.example.feature.locations

data class LocationsListScreen<T> (
    val isLoading: Boolean = false,
    val items: List<T> = emptyList(),
    val error: String? = null
)

