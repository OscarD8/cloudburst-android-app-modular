package com.example.domain.repository

import com.example.domain.model.Location
import com.example.domain.model.LocationCategory
import kotlinx.coroutines.flow.Flow


interface LocationRepository {

    fun getLocationsAll() : Flow<List<Location>>
    fun getLocationsByCategory(category: LocationCategory) : Flow<List<Location>>
    suspend fun getRandomId() : Long
    fun getLocationById(id: Long) : Flow<Location?>
    fun getFavourites() : Flow<List<Location>>
    suspend fun getCategories() : List<LocationCategory>
    fun toggleFavourite(locationId: Long)

}