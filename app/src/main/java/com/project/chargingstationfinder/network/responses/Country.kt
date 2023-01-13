package com.project.chargingstationfinder.network.responses

import com.google.gson.annotations.SerializedName


data class Country (

  @SerializedName("ISOCode"       ) var ISOCode       : String? = null,
  @SerializedName("ContinentCode" ) var ContinentCode : String? = null,
  @SerializedName("ID"            ) var ID            : Int?    = null,
  @SerializedName("description"   ) var description   : String? = null

)