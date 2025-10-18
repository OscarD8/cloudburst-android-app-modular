package com.example.data

import androidx.annotation.StringRes
import com.example.domain.model.LocationCategory

object FakeApiService {
    internal data class LocationDataEntry (
        val id: Long,
        @StringRes val nameRes: Int,
        @StringRes val addressRes: Int,
        @StringRes val descriptionRes: Int,
        val imageIdentifier: String,
        val rating: Int,
        val isCarbonCapturing: Boolean,
        val category: LocationCategory,
        val isFavourite: Boolean
    )

    // --- Restaurants ---
    internal val allRestaurants = listOf(
        LocationDataEntry(
            id = 0L,
            nameRes = R.string.restaurant_name_mycelia_feast,
            addressRes = R.string.restaurant_address_mycelia_feast,
            descriptionRes = R.string.restaurant_description_mycelia_feast,
            imageIdentifier = "restaurant_mycelia_feast",
            rating = 4,
            isCarbonCapturing = true,
            category = LocationCategory.RESTAURANTS,
            isFavourite = false
        ),
        LocationDataEntry(
            id = 1L,
            nameRes = R.string.restaurant_name_neon_nectar,
            addressRes = R.string.restaurant_address_neon_nectar,
            descriptionRes = R.string.restaurant_description_neon_nectar,
            imageIdentifier = "restaurant_neon_nectar",
            rating = 5,
            isCarbonCapturing = false,
            category = LocationCategory.RESTAURANTS,
            isFavourite = false
        ),
        LocationDataEntry(
            id = 2L,
            nameRes = R.string.restaurant_name_solaris_table,
            addressRes = R.string.restaurant_address_solaris_table,
            descriptionRes = R.string.restaurant_description_solaris_table,
            imageIdentifier = "restaurant_solaris_table",
            rating = 3,
            isCarbonCapturing = true,
            category = LocationCategory.RESTAURANTS,
            isFavourite = false
        ),
        LocationDataEntry(
            id = 3L,
            nameRes = R.string.restaurant_name_fungal_bloom,
            addressRes = R.string.restaurant_address_fungal_bloom,
            descriptionRes = R.string.restaurant_description_fungal_bloom,
            imageIdentifier = "restaurant_fungal_bloom",
            rating = 4,
            isCarbonCapturing = false,
            category = LocationCategory.RESTAURANTS,
            isFavourite = false
        ),
        LocationDataEntry(
            id = 4L,
            nameRes = R.string.restaurant_name_shimmering_spoon,
            addressRes = R.string.restaurant_address_shimmering_spoon,
            descriptionRes = R.string.restaurant_description_shimmering_spoon,
            imageIdentifier = "restaurant_shimmering_spoon",
            rating = 5,
            isCarbonCapturing = true,
            category = LocationCategory.RESTAURANTS,
            isFavourite = false
        ),
        LocationDataEntry(
            id = 5L,
            nameRes = R.string.restaurant_name_aurora_harvest,
            addressRes = R.string.restaurant_address_aurora_harvest,
            descriptionRes = R.string.restaurant_description_aurora_harvest,
            imageIdentifier = "restaurant_aurora_harvest",
            rating = 2,
            isCarbonCapturing = false,
            category = LocationCategory.RESTAURANTS,
            isFavourite = false
        )
    )

    // --- Cafes ---
    internal val allCafes = listOf(
        LocationDataEntry(6L, R.string.cafe_name_lumina_brew, R.string.cafe_address_lumina_brew, R.string.cafe_description_lumina_brew, "cafe_lumina_brew", 4, true, LocationCategory.CAFES, false),
        LocationDataEntry(7L, R.string.cafe_name_spore_and_steam, R.string.cafe_address_spore_and_steam, R.string.cafe_description_spore_and_steam, "cafe_spore_and_steam", 3, false, LocationCategory.CAFES, false),
        LocationDataEntry(8L, R.string.cafe_name_neon_drip, R.string.cafe_address_neon_drip, R.string.cafe_description_neon_drip, "cafe_neon_drip", 5, true, LocationCategory.CAFES, false),
        LocationDataEntry(9L, R.string.cafe_name_solar_sip, R.string.cafe_address_solar_sip, R.string.cafe_description_solar_sip, "cafe_solar_sip", 2, false, LocationCategory.CAFES, false),
        LocationDataEntry(10L, R.string.cafe_name_fungal_cup, R.string.cafe_address_fungal_cup, R.string.cafe_description_fungal_cup, "cafe_fungal_cup", 4, true, LocationCategory.CAFES, false),
        LocationDataEntry(11L, R.string.cafe_name_aurora_bean, R.string.cafe_address_aurora_bean, R.string.cafe_description_aurora_bean, "cafe_aurora_bean", 5, false, LocationCategory.CAFES, false)
    )

    // --- Parks ---
    internal val allParks = listOf(
        LocationDataEntry(12L, R.string.park_name_luminescent_grove, R.string.park_address_luminescent_grove, R.string.park_description_luminescent_grove, "park_luminescent_grove", 5, true, LocationCategory.PARKS, false),
        LocationDataEntry(13L, R.string.park_name_mycelium_gardens, R.string.park_address_mycelium_gardens, R.string.park_description_mycelium_gardens, "park_mycelium_gardens", 4, false, LocationCategory.PARKS, false),
        LocationDataEntry(14L, R.string.park_name_neon_canopy, R.string.park_address_neon_canopy, R.string.park_description_neon_canopy, "park_neon_canopy", 3, true, LocationCategory.PARKS, false),
        LocationDataEntry(15L, R.string.park_name_solar_arcadia, R.string.park_address_solar_arcadia, R.string.park_description_solar_arcadia, "park_solar_arcadia", 5, false, LocationCategory.PARKS, false),
        LocationDataEntry(16L, R.string.park_name_fungal_glade, R.string.park_address_fungal_glade, R.string.park_description_fungal_glade, "park_fungal_glade", 4, true, LocationCategory.PARKS, false),
        LocationDataEntry(17L, R.string.park_name_aurora_terrace, R.string.park_address_aurora_terrace, R.string.park_description_aurora_terrace, "park_aurora_terrace", 2, false, LocationCategory.PARKS, false)
    )

    // --- Temples ---
    internal val allTemples = listOf(
        LocationDataEntry(18L, R.string.temple_name_sunspire_sanctuary, R.string.temple_address_sunspire_sanctuary, R.string.temple_description_sunspire_sanctuary, "temple_sunspire_sanctuary", 4, false, LocationCategory.TEMPLES, false),
        LocationDataEntry(19L, R.string.temple_name_helios_haven, R.string.temple_address_helios_haven, R.string.temple_description_helios_haven, "temple_helios_haven", 5, true, LocationCategory.TEMPLES, false),
        LocationDataEntry(20L, R.string.temple_name_radiant_dome, R.string.temple_address_radiant_dome, R.string.temple_description_radiant_dome, "temple_radiant_dome", 3, false, LocationCategory.TEMPLES, false),
        LocationDataEntry(21L, R.string.temple_name_solstice_shrine, R.string.temple_address_solstice_shrine, R.string.temple_description_solstice_shrine, "temple_solstice_shrine", 5, true, LocationCategory.TEMPLES, false),
        LocationDataEntry(22L, R.string.temple_name_aurora_altar, R.string.temple_address_aurora_altar, R.string.temple_description_aurora_altar, "temple_aurora_altar", 2, false, LocationCategory.TEMPLES, false),
        LocationDataEntry(23L, R.string.temple_name_celestial_sun, R.string.temple_address_celestial_sun, R.string.temple_description_celestial_sun, "temple_celestial_sun", 4, true, LocationCategory.TEMPLES, false)
    )

    // --- Printers ---
    internal val allPrinters = listOf(
        LocationDataEntry(24L, R.string.printer_name_spore_forge, R.string.printer_address_spore_forge, R.string.printer_description_spore_forge, "printer_spore_forge", 3, true, LocationCategory.PRINTERS, false),
        LocationDataEntry(25L, R.string.printer_name_mycelium_works, R.string.printer_address_mycelium_works, R.string.printer_description_mycelium_works, "printer_mycelium_works", 5, false, LocationCategory.PRINTERS, false),
        LocationDataEntry(26L, R.string.printer_name_biofab_hub, R.string.printer_address_biofab_hub, R.string.printer_description_biofab_hub, "printer_biofab_hub", 4, true, LocationCategory.PRINTERS, false),
        LocationDataEntry(27L, R.string.printer_name_fungal_foundry, R.string.printer_address_fungal_foundry, R.string.printer_description_fungal_foundry, "printer_fungal_foundry", 2, false, LocationCategory.PRINTERS, false),
        LocationDataEntry(28L, R.string.printer_name_aurora_fabricators, R.string.printer_address_aurora_fabricators, R.string.printer_description_aurora_fabricators, "printer_aurora_fabricators", 5, true, LocationCategory.PRINTERS, false),
        LocationDataEntry(29L, R.string.printer_name_neon_grow, R.string.printer_address_neon_grow, R.string.printer_description_neon_grow, "printer_neon_grow", 3, false, LocationCategory.PRINTERS, false)
    )

    internal val allLocations = allRestaurants + allCafes + allParks + allTemples + allPrinters

    internal fun getLocationsByCategory(category: LocationCategory): List<LocationDataEntry> {
        return when (category) {
            LocationCategory.RESTAURANTS -> allRestaurants
            LocationCategory.CAFES -> allCafes
            LocationCategory.PARKS -> allParks
            LocationCategory.TEMPLES -> allTemples
            LocationCategory.PRINTERS -> allPrinters
        }
    }

    internal fun getLocationById(locationId: Long): LocationDataEntry {
        return allLocations.first() {it.id == locationId}
    }
}