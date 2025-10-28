package com.example.domain.usecase

import com.example.domain.model.Location
import com.example.domain.repository.LocationRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class GetLocationByIdUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke(id: Long): Flow<Location?> = locationRepository.getLocationById(id)
}