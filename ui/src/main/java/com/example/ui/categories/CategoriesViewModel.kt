package com.example.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Location
import com.example.domain.model.LocationCategory
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.ui.common.ListScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Stateholder for the [CategoriesScreen].
 *
 * @param getCategoriesUseCase Use case to get the categories - a list of all LocationCategory types.
 */
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListScreenUiState<LocationCategory>())
    val uiState: StateFlow<ListScreenUiState<LocationCategory>> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val categories = getCategoriesUseCase.invoke()

            _uiState.update { it.copy(
                isLoading = false,
                items = categories
            ) }
        }
    }
}