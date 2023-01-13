package com.project.chargingstationfinder.network.responses

import com.google.gson.annotations.SerializedName


data class AddressInfo(

    val ID: Int?,
    val description: String?,
    val AddressLine1: String?,
    val AddressLine2: String?,
    val Town: String?,
    val StateOrProvince: String?,
    val Postcode: String?,
    val CountryID: Int?,
    //val Country           : Country? = Country(),
    val Latitude: Double?,
    val Longitude: Double?,
    val ContactTelephone1: String?,
    val ContactTelephone2: String?,
    val ContactEmail: String?,
    //val AccessComments    : String?  ,
    val RelatedURL: String?,
    val Distance: String?,
    val DistanceUnit: Int?

)