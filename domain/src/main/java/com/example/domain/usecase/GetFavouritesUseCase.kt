package com.example.domain.usecase

import com.example.domain.repository.LocationRepository
import jakarta.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke() = locationRepository.getFavourites()
}
