package com.example.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LocationCategory
import com.example.domain.usecase.GetFavouritesUseCase
import com.example.domain.usecase.ToggleFavouriteUseCase
import com.example.ui.common.LocationMapper
import com.example.ui.locations.LocationUiModel
import com.example.ui.locations.list.ListScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/*
* Notes:
* had problem here with compiler saying it could not infer the type of uiState because
* im assigning it a specialised type of ListScreenUiState<LocationUiModel> and it was as if it
* was interpreting it as a generic ListScreenUiState<Any>, or it just couldn't infer the type.
* I'd put the parameter name wrong... so unable to infer type because the mapping was returning
* an incorrect call to ListScreenUiState.
 */

/**
 * ViewModel for the Favourites screen.
 *
 * @property getFavouritesUseCase Use case to get the favourites.
 * @property mapper Mapper to map the domain model to the UI model.
 * @property toggleFavouriteUseCase Use case to toggle the favourite.
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val mapper: LocationMapper,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase
) : ViewModel() {
    val uiState: StateFlow<ListScreenUiState<LocationUiModel>> =
        getFavouritesUseCase.invoke() // does the error check come here?
            .map { locationsFromDomain ->
                val locationsForUi = locationsFromDomain.map { location ->
                    mapper.toUiModel(location)
                }
                ListScreenUiState(items = locationsForUi, isLoading = false, error = null)
            }
            .stateIn( // Converts this "cold" flow into a "hot" StateFlow for the UI to observe.
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000), // Keep the flow alive for 5s after the UI stops watching to save state.

                initialValue = ListScreenUiState(isLoading = true) // Display for loading.
            )


    fun categoriseFavourites(): Map<LocationCategory, List<LocationUiModel>> {
        val favourites = uiState.value.items
        val groupedFavourites = favourites.groupBy { it.category }
        return groupedFavourites
    }

    fun toggleFavourite(id: Long) {
        toggleFavouriteUseCase.invoke(id)
    }

}