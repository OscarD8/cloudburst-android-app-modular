package com.example.ui.locations.detail

import android.app.Application
import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetLocationByIdUseCase
import com.example.domain.usecase.ToggleFavouriteUseCase
import com.example.ui.R
import com.example.ui.common.LocationMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Stateholder for the LocationDetail screen.
 */
@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val locationMapper: LocationMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState: StateFlow<LocationDetailUiState> = _uiState.asStateFlow()

    val locationId: Long = checkNotNull(savedStateHandle["locationId"])


    init {
        loadLocation(locationId)
    }

    private fun loadLocation(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val location = getLocationByIdUseCase(id)

            if (location != null) {
                val locationUiModel = locationMapper.toUiModel(location)
                _uiState.update { it.copy(isLoading = false, location = locationUiModel) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = R.string.error_location_not_found) }
            }
        }
    }

    fun toggleLocationFavourite() {
        toggleFavouriteUseCase.invoke(locationId)
    }
}