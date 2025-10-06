package com.example.domain.repository

import com.example.domain.model.Location

interface LocationRepository {
    fun getAllLocations() : List<Location>
}