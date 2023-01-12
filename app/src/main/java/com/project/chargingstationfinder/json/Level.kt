package com.project.chargingstationfinder.json

import com.google.gson.annotations.SerializedName


data class Level (

  @SerializedName("Comments"            ) var Comments            : String?  = null,
  @SerializedName("IsFastChargeCapable" ) var IsFastChargeCapable : Boolean? = null,
  @SerializedName("ID"                  ) var ID                  : Int?     = null,
  @SerializedName("description"         ) var description         : String?  = null

)