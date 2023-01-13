package com.project.chargingstationfinder.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChargingStation(
   /* var DataProvider: DataProvider? = DataProvider(),
    var OperatorInfo: OperatorInfo? = OperatorInfo(),
    var UsageType: UsageType? = UsageType(),
    var StatusType: StatusType? = StatusType(),
    var SubmissionStatus: SubmissionStatus? = SubmissionStatus(),*/
    var PercentageSimilarity: String? = null,
    @PrimaryKey
    var ID: Int? = null,
    var UUID: String? = null,
    /*var AddressInfo: AddressInfo? = AddressInfo(),
    var Connections: ArrayList<Connections>? = ArrayList<Connections>(),*/
    var NumberOfPoints: Int? = null,
) {

}