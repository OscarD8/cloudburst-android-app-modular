package com.example.domain

import jakarta.inject.Inject


class GetRandomFactUseCase @Inject constructor(
    private val factRepository: FactRepository
) {
    fun execute() = factRepository.getFactRes() //  overridden in the data layer
}