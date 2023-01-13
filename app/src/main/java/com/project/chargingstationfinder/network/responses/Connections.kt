package com.project.chargingstationfinder.network.responses

import com.google.gson.annotations.SerializedName


data class Connections(

    val ID: Int?,
    val ConnectionTypeID: Int?,
    val ConnectionType: ConnectionType?,
    val Reference: String?,
    val StatusTypeID: Int?,
    val StatusType: StatusType?,
    val LevelID: Int?,
    val Level: Level?,
    val Amps: String?,
    val Voltage: String?,
    val PowerKW: Double?,
    val CurrentTypeID: Int?,
    val CurrentType: CurrentType?,
    val Quantity: Int?,
    val Comments: String?

)