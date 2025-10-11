package com.example.ui.locations.list

data class ListScreenUiState<T> (
    val isLoading: Boolean = false,
    val items: List<T> = emptyList(),
    val error: String? = null
)