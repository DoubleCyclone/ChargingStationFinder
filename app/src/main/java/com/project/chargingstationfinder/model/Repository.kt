package com.project.chargingstationfinder.model

class Repository(private val apiService: ApiService) {
    fun getChargingStations(
        countryCode: String,
        latitude: Double,
        longitude: Double,
        distance: Int,
        distanceUnit: Int,
        apiKey: String
    ) = apiService.getPois(countryCode, latitude, longitude, distance, distanceUnit, apiKey)

}