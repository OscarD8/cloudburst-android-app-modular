package com.example.ui.locations.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LocationCategory
import com.example.domain.usecase.GetLocationsByCategoryUseCase
import com.example.ui.locations.list.ListScreenUiState
import com.example.ui.common.LocationMapper
import com.example.ui.locations.LocationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LocationsListViewModel @Inject constructor(
    private val getLocationsByCategoryUseCase: GetLocationsByCategoryUseCase,
    private val locationMapper: LocationMapper
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListScreenUiState<LocationUiModel>())
    val uiState: StateFlow<ListScreenUiState<LocationUiModel>> = _uiState.asStateFlow()

    init {

    }

    private fun loadLocations(category: LocationCategory) {
        viewModelScope.launch {
            val locationsFromDomain = getLocationsByCategoryUseCase.invoke(category)

            val locationsForUi = locationsFromDomain.map { location ->
                locationMapper.toUiModel(location)
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    items = locationsForUi
                )
            }
        }
    }

}