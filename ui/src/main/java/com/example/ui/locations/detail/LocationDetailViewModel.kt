package com.example.ui.locations.detail

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetLocationByIdUseCase
import com.example.ui.R
import com.example.ui.common.LocationMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val application: Application,
    private val locationMapper: LocationMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState: StateFlow<LocationDetailUiState> = _uiState.asStateFlow()

    init {
        val locationId: String? = savedStateHandle["locationId"]

        locationId?.toLongOrNull()?.let { id ->
            loadLocation(id)
        } ?: run {
            _uiState.update { it.copy (
                isLoading = false,
                error = application.getString(R.string.error_location))
            }
        }
    }

    private fun loadLocation(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val location = getLocationByIdUseCase(id)

            val locationUiModel = locationMapper.to(location)
        }
    }
}