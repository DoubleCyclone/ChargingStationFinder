package com.project.chargingstationfinder.network.responses

import com.google.gson.annotations.SerializedName


data class StatusType(

    val IsOperational: Boolean?,
    val IsUserSelectable: Boolean?,
    val ID: Int?,
    val description: String?

)