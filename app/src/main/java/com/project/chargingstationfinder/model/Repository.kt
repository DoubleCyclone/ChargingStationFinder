package com.project.chargingstationfinder.model

class Repository(private val apiClient: ApiClient) {
    fun getChargingStations(
        countryCode: String,
        latitude: Float,
        longitude: Float,
        distance: Int,
        distanceUnit: Int,
        apiKey: String
    ) = apiClient.getPois(countryCode, latitude, longitude, distance, distanceUnit, apiKey)
}