package com.example.domain.repository

import com.example.domain.model.Location
import com.example.domain.model.LocationCategory

interface LocationRepository {

    suspend fun getLocationsAll() : List<Location>
    suspend fun getLocationsByCategory(category: LocationCategory) : List<Location>
    suspend fun getLocationById(id: Long) : Location?
    suspend fun getCategories() : List<LocationCategory>

}