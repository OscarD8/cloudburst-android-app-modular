package com.example.ui.locations.detail

import android.app.Application
import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    val locationId: Long = checkNotNull(savedStateHandle["locationId"])

    val uiState: StateFlow<LocationDetailUiState> =
        getLocationByIdUseCase(locationId)
            .map { location ->
                if (location != null) {
                    val uiModel = locationMapper.toUiModel(location)
                    LocationDetailUiState(location = uiModel)
                } else {
                    LocationDetailUiState(error = R.string.error_location_not_found)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LocationDetailUiState(isLoading = true)
            )



    fun toggleLocationFavourite(locationId: Long) {
        Log.d("LocationDetailViewModel", "toggleLocationFavourite: $locationId")
        viewModelScope.launch {
            toggleFavouriteUseCase.invoke(locationId)
        }
    }
}