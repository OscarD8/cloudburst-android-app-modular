package com.example.ui.common

data class ListScreenUiState<T> (
    val isLoading: Boolean = false,
    val items: List<T> = emptyList(),
    val error: String? = null
)