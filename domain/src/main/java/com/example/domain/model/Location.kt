package com.example.domain.model

data class Location(
    val id: Long,
    val name: String,
    val address: String,
    val description: String,
    val imageIdentifier: String,
    val rating: Int,
    val isCarbonCapturing: Boolean,
    val category: LocationCategory,
    val isFavourite: Boolean
)

enum class LocationCategory{
    RESTAURANTS, CAFES, PARKS, TEMPLES, PRINTERS, UNKNOWN
}
