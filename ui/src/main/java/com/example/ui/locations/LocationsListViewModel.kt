package com.example.ui.locations

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetLocationsAllUseCase
import com.example.ui.common.ListScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class LocationsListViewModel @Inject constructor(
    private val getLocationsAllUseCase: GetLocationsAllUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListScreenUiState<Location>())
    val uiState: StateFlow<LocationsListScreen<LocationDetailScreen>> = _uiState.asStateFlow()

    init {

    }

    private fun Location.toUiModel
}