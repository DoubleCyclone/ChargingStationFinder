package com.project.chargingstationfinder.network

import com.google.gson.annotations.SerializedName


data class StatusType (

  @SerializedName("IsOperational"    ) var IsOperational    : Boolean? = null,
  @SerializedName("IsUserSelectable" ) var IsUserSelectable : Boolean? = null,
  @SerializedName("ID"               ) var ID               : Int?     = null,
  @SerializedName("description"      ) var description      : String?  = null

)