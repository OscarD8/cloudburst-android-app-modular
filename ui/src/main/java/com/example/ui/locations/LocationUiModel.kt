package com.example.ui.locations

import androidx.annotation.DrawableRes
import com.example.domain.model.LocationCategory

data class LocationUiModel (
    val id: Long,
    val name: String,
    val address: String,
    val description: String,
    @DrawableRes val imageIdentifier: Int,
    val rating: Int,
    val isCarbonCapturing: Boolean,
    val category: LocationCategory,
    val isFavourite: Boolean,
    val isExpanded: Boolean
)
