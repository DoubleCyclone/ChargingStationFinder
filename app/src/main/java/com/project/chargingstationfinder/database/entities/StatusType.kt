package com.project.chargingstationfinder.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class StatusType(
    var IsOperational: Boolean? = null,
    var IsUserSelectable: Boolean? = null,
    @PrimaryKey
    var ID: Int? = null,
    var description: String? = null
)