package com.example.data

import android.content.Context
import com.example.domain.model.Location
import com.example.domain.model.LocationCategory
import com.example.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val res: Context
) : LocationRepository {

    override fun getLocationsAll(): Flow<List<Location>> {
        val sourceFlow = FakeApiService.allLocations

        return sourceFlow.map { dataEntries ->
            dataEntries.map { dataEntry ->
                mapLocationToDomainModel(dataEntry)
            }
        }
    }

    // assuming that we can always fetch a list of items by locationCategory as lC is exhaustive and lists are hard-coded..
    override fun getLocationsByCategory(category: LocationCategory): Flow<List<Location>> {
        val sourceFlow = FakeApiService.getLocationsByCategory(category)

        return sourceFlow.map { dataEntries ->
            dataEntries.map { dataEntry ->
                mapLocationToDomainModel(dataEntry)
            }
        }
    }

    override fun getLocationById(id: Long): Flow<Location?> {
        return FakeApiService.getLocationStreamById(id)
            .map { dataEntry ->
                dataEntry?.let { mapLocationToDomainModel(it) }
            }
    }

    override suspend fun getCategories(): List<LocationCategory> {
        return LocationCategory.entries
    }

    override fun toggleFavourite(locationId: Long) {
        FakeApiService.toggleFavourite(locationId)
    }

    private fun mapLocationToDomainModel(
        locationDataEntry: FakeApiService.LocationDataEntry
    ) : Location {
        return Location(
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
