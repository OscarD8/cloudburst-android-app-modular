package com.example.ui.locations.detail

import androidx.annotation.DrawableRes
import com.example.domain.model.LocationCategory


data class LocationDetailScreen(
    val name: String,
    val address: String,
    val rating: Int,
    val isCarbonCapturing: Boolean,
    val category: LocationCategory,
    val description: String,
    @DrawableRes val imageId: Int
)

fun LocationDetailScreen() {

}