package com.example.ui.locations.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LocationCategory
import com.example.domain.usecase.GetLocationsByCategoryUseCase
import com.example.domain.usecase.ToggleFavouriteUseCase
import com.example.ui.R
import com.example.ui.common.LocationMapper
import com.example.ui.locations.LocationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
 * A viewmodel for the locations list screen.
 * Responsible for loading the locations from the domain layer and mapping them to the UI model.
 * Provides a method to toggle the expanded state of a location item.
 *
 * @property getLocationsByCategoryUseCase The use case to get locations by category.
 * @property locationMapper The mapper to map the domain model to the UI model.
 * @property savedStateHandle The saved state handle to get the category from the navigation arguments.
 */
@HiltViewModel
class LocationsListViewModel @Inject constructor(
    private val getLocationsByCategoryUseCase: GetLocationsByCategoryUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val locationMapper: LocationMapper,
    private val savedStateHandle: SavedStateHandle // bridging navigation arguments to the viewmodel lifecycle
) : ViewModel() {

    val categoryName: String = checkNotNull(savedStateHandle["category"]) // fail fast, screen should not exist without category argument
    private val locationCategory: LocationCategory = enumValueOf<LocationCategory>(categoryName)

    val uiState: StateFlow<ListScreenUiState<LocationUiModel>> =
        getLocationsByCategoryUseCase.invoke(locationCategory)
            .map { locationsFromDomain ->
                val locationsForUi = locationsFromDomain.map { location ->
                    locationMapper.toUiModel(location)
                }
                ListScreenUiState(items = locationsForUi, isLoading = false, error = null)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ListScreenUiState(isLoading = true)
            )

    fun getCategoryImageRes(): Int {
        return when (locationCategory) {
            LocationCategory.RESTAURANTS -> R.drawable.rest_bg
            else -> R.drawable.list_master_bg
        }
    }

    fun toggleLocationItemFavourite(locationId: Long) {
        Log.d("LocationsListViewModel", "toggleLocationItemFavourite: $locationId")
        viewModelScope.launch {
            toggleFavouriteUseCase.invoke(locationId)
        }
    }

}