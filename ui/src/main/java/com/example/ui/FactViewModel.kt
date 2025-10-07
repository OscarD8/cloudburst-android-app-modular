package com.example.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.domain.GetRandomFactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel // telling Hilt you are in charge of creating this ViewModel
class FactViewModel @Inject constructor(
    private val getRandomFactUseCase: GetRandomFactUseCase
)  : ViewModel()  {
    private var _fact = mutableStateOf("")
    val fact: State<String> = _fact

    init {
        _fact.value = getRandomFactUseCase.execute()
    }
}