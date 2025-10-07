package com.example.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Location
import com.example.domain.usecase.GetLocationsAllUseCase
import com.example.domain.usecase.GetLocationsByCategoryUseCase
import com.example.ui.common.ListScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LocationsListViewModel @Inject constructor(
    private val getLocationsByCategoryUseCase: GetLocationsByCategoryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListScreenUiState<LocationUiState>())
    val uiState: StateFlow<ListScreenUiState<LocationUiState>> = _uiState.asStateFlow()

    init {

    }

//    private fun loadLocations() {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true) }
//
//            val  locationsFromDomain: List<Location> = getLocationsByCategoryUseCase.invoke(// category comes from where?)
//        }
//    }

}