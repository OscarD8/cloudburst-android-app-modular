package com.example.data

import android.content.Context
import com.example.domain.model.Location
import com.example.domain.model.LocationCategory
import com.example.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val res: Context
) : LocationRepository {

    override suspend fun getLocationsAll(): List<Location> {
        return FakeApiService.allLocations.map { locationDataEntry ->
            Location(
                id = locationDataEntry.id,
                name = res.getString(locationDataEntry.nameRes),
                address = res.getString(locationDataEntry.addressRes),
                description = res.getString(locationDataEntry.descriptionRes),
                imageIdentifier = locationDataEntry.imageIdentifier,
                rating = locationDataEntry.rating,
                isCarbonCapturing = locationDataEntry.isCarbonCapturing,
                category = locationDataEntry.category,
                isFavourite = locationDataEntry.isFavourite
            )
        }
    }

//    override suspend fun getLocationsByCategory(category: LocationCategory): List<Location> {
//        val locations = FakeApiService.getLocationsByCategory(category)
//
//    }


    // TODO helper function for mapping

}
