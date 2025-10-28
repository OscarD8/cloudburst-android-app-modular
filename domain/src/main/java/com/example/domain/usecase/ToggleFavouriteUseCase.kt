package com.example.domain.usecase

import com.example.domain.repository.LocationRepository
import jakarta.inject.Inject


class ToggleFavouriteUseCase @Inject constructor(
    private val repository: LocationRepository
){
    operator fun invoke(locationId: Long) {
        repository.toggleFavourite(locationId)
    }
}