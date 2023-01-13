package com.project.chargingstationfinder.network.responses

import com.google.gson.annotations.SerializedName


data class ConnectionType (

  @SerializedName("FormalName"     ) var FormalName     : String?  = null,
  @SerializedName("IsDiscontinued" ) var IsDiscontinued : Boolean? = null,
  @SerializedName("IsObsolete"     ) var IsObsolete     : Boolean? = null,
  @SerializedName("ID"             ) var ID             : Int?     = null,
  @SerializedName("description"    ) var description    : String?  = null

)