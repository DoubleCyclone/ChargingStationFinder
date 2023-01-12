package com.project.chargingstationfinder.json

import com.google.gson.annotations.SerializedName


data class DataProviderStatusType (

  @SerializedName("IsProviderEnabled" ) var IsProviderEnabled : Boolean? = null,
  @SerializedName("ID"                ) var ID                : Int?     = null,
  @SerializedName("description"       ) var description       : String?  = null

)