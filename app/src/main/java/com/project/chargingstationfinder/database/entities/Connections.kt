package com.project.chargingstationfinder.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Connections(
    @PrimaryKey
    var ID: Int? = null,
    var ConnectionTypeID: Int? = null,
    //var ConnectionType   : ConnectionType?  ,
    var Reference: String? = null,
    var StatusTypeID: Int? = null,
    //var StatusType       : StatusType?      ,
    var LevelID: Int? = null,
    //var Level            : Level?           ,
    var Amps: String? = null,
    var Voltage: String? = null,
    var PowerKW: Double? = null,
    var CurrentTypeID: Int? = null,
    //var CurrentType      : CurrentType?     ,
    var Quantity: Int? = null,
    var Comments: String? = null
)