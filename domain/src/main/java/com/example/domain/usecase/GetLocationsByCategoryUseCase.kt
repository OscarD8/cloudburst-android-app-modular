package com.example.domain.usecase

import com.example.domain.model.LocationCategory
import com.example.domain.repository.LocationRepository
import jakarta.inject.Inject

class GetLocationsByCategoryUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(category: LocationCategory) =
        locationRepository.getLocationsByCategory(category)
}


