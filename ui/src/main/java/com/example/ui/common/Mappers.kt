package com.example.ui.common

import android.content.Context
import com.example.domain.model.Location
import com.example.ui.R
import com.example.ui.locations.LocationUiModel

/**
 * A reusable mapper function to convert a domain Location into a UI-ready LocationUiModel.
 */
fun Location.toUiModel(context: Context): LocationUiModel {
    val resources = context.resources
    val packageName = context.packageName

    val imageResId = resources.getIdentifier(
        this.imageIdentifier,
        "drawable",
        packageName
    )

    return LocationUiModel(
        id = this.id,
        name = this.name,
        address = this.address,
        description = this.description,
        imageIdentifier = if (imageResId != 0) imageResId else R.drawable.home_cover_image,
        rating = this.rating,
        isCarbonCapturing = this.isCarbonCapturing,
        category = this.category,
        isFavourite = this.isFavourite,
        isExpanded = false
    )
}