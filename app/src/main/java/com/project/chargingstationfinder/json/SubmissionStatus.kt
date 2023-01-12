package com.project.chargingstationfinder.json

import com.google.gson.annotations.SerializedName


data class SubmissionStatus (

  @SerializedName("IsLive"      ) var IsLive      : Boolean? = null,
  @SerializedName("ID"          ) var ID          : Int?     = null,
  @SerializedName("description" ) var description : String?  = null

)