package com.example.ui.locations.list

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LocationCategory
import com.example.domain.usecase.GetLocationsByCategoryUseCase
import com.example.ui.R
import com.example.ui.common.ListScreenUiState
import com.example.ui.common.LocationMapper
import com.example.ui.locations.LocationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val locationMapper: LocationMapper,
    private val savedStateHandle: SavedStateHandle // bridging navigation arguments to the viewmodel lifecycle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListScreenUiState<LocationUiModel>())
    val uiState: StateFlow<ListScreenUiState<LocationUiModel>> = _uiState.asStateFlow()

    val categoryName: String = checkNotNull(savedStateHandle["category"])
    private val locationCategory: LocationCategory = enumValueOf<LocationCategory>(categoryName)


    init {
        loadLocations(locationCategory)
    }

    /*
     * Loads the locations from the domain layer and maps them to the UI model.
     */
    private fun loadLocations(category: LocationCategory) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

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

    fun getCategoryImageRes(): Int {
        return when (locationCategory) {
            LocationCategory.RESTAURANTS -> R.drawable.rest_bg
            else -> R.drawable.list_master_bg
        }
    }

    fun toggleLocationItemExpanded(itemId: Long) {
        _uiState.update { currentState ->
            val updatedItems = currentState.items.map { item ->
                if (itemId == item.id) {
                    item.copy(isExpanded = !item.isExpanded)
                } else {
                    item
                }
            }
            currentState.copy(items = updatedItems)
        }
    }

    fun toggleLocationItemFavourite(itemId: Long) {
        Log.d("LocationsListViewModel", "toggleLocationItemFavourite: $itemId")
        _uiState.update { currentState ->
            val updatedItems = currentState.items.map { item ->
                if (item.id == itemId) {
                    item.copy(isFavourite = !item.isFavourite)
                } else {
                    item
                }
            }
            currentState.copy(items = updatedItems)
        }
    }

}