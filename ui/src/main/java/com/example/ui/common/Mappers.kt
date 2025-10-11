package com.example.ui.common

import android.content.Context
import com.example.domain.model.Location
import com.example.ui.R
import com.example.ui.locations.LocationUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

/**
 * A reusable mapper function to convert a domain Location into a UI-ready LocationUiModel.
 */
class LocationMapper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun toUiModel(location: Location): LocationUiModel {
        val resources = context.resources
        val packageName = context.packageName

        val imageResId = resources.getIdentifier(
            location.imageIdentifier,
            "drawable",
            packageName
        )

        return LocationUiModel(
            id = location.id,
            name = location.name,
            address = location.address,
            description = location.description,
            imageIdentifier = if (imageResId != 0) imageResId else R.drawable.home_cover_image,
            rating = location.rating,
            isCarbonCapturing = location.isCarbonCapturing,
            category = location.category,
            isFavourite = location.isFavourite,
            isExpanded = false
        )
    }
}