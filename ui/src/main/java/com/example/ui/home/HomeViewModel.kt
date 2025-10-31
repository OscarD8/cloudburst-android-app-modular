package com.example.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetRandomIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomIdUseCase: GetRandomIdUseCase
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<Long> (
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val eventFlow = _eventFlow.asSharedFlow()

    fun onExploreClicked() {
        viewModelScope.launch {
            val id = getRandomIdUseCase()
            Log.d("HomeViewModel", "onExploreClicked: $id")
            _eventFlow.emit(id)
        }
    }
}

