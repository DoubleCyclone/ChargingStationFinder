package com.project.chargingstationfinder.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddressInfo(
    @PrimaryKey
    var ID: Int? = null,
    var description: String? = null,
    var AddressLine1: String? = null,
    var AddressLine2: String? = null,
    var Town: String? = null,
    var StateOrProvince: String? = null,
    var Postcode: String? = null,
    var CountryID: Int? = null,
    //var Country           : Country? = Country(),
    var Latitude: Double? = null,
    var Longitude: Double? = null,
    var ContactTelephone1: String? = null,
    var ContactTelephone2: String? = null,
    var ContactEmail: String? = null,
    //var AccessComments    : String?  = null,
    var RelatedURL: String? = null,
    var Distance: String? = null,
    var DistanceUnit: Int? = null
)