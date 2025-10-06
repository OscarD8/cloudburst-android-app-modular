package com.example.feature.locations

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class LocationsListViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LocationsListScreen<LocationDetailScreen>())
    val uiState: StateFlow<LocationsListScreen<LocationDetailScreen>> = _uiState.asStateFlow()

    init {

    }
}