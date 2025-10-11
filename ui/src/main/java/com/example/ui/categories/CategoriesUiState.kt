package com.example.ui.categories

import com.example.domain.model.LocationCategory

data class CategoriesUiState (
    val categories: List<LocationCategory> = emptyList(),
    val isLoading: Boolean = false
)