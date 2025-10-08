package com.example.domain.usecase

import com.example.domain.repository.LocationRepository
import jakarta.inject.Inject


class GetLocationByIdUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(id: Long) = locationRepository.getLocationById(id)
}