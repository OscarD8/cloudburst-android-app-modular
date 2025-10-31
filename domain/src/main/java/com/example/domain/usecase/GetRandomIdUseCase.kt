package com.example.domain.usecase

import com.example.domain.repository.LocationRepository
import jakarta.inject.Inject

class GetRandomIdUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke() = locationRepository.getRandomId()
}