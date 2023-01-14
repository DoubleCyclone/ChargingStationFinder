package com.project.chargingstationfinder.network

import com.project.chargingstationfinder.database.entities.ChargingStation

data class ChargingStationsResponse(
    val chargingStations: List<ChargingStation>
)