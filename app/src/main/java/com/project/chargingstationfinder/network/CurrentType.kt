package com.project.chargingstationfinder.network

import com.google.gson.annotations.SerializedName


data class CurrentType (

  @SerializedName("Description" ) var Description : String? = null,
  @SerializedName("ID"          ) var ID          : Int?    = null,
  @SerializedName("description" ) var description2 : String? = null

)