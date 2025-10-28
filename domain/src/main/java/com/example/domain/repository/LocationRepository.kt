package com.example.domain.repository

import com.example.domain.model.Location
import com.example.domain.model.LocationCategory
import kotlinx.coroutines.flow.Flow


interface LocationRepository {

    fun getLocationsAll() : Flow<List<Location>>
    fun getLocationsByCategory(category: LocationCategory) : Flow<List<Location>>
    fun getLocationById(id: Long) : Flow<Location?>
    suspend fun getCategories() : List<LocationCategory>
    fun toggleFavourite(locationId: Long)

}