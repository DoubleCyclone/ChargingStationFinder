package com.project.chargingstationfinder.network.responses

import com.google.gson.annotations.SerializedName


data class ChargingStation(

    val DataProvider: DataProvider?,
    val OperatorInfo: OperatorInfo?,
    val UsageType: UsageType?,
    val StatusType: StatusType?,
    val SubmissionStatus: SubmissionStatus?,
    val UserComments: String?,
    val PercentageSimilarity: String?,
    val MediaItems: String?,
    val IsRecentlyVerified: Boolean?,
    val DateLastVerified: String?,
    val ID: Int?,
    val UUID: String?,
    val ParentChargePointID: String?,
    val DataProviderID: Int?,
    val DataProvidersReference: String?,
    val OperatorID: Int?,
    val OperatorsReference: String?,
    val UsageTypeID: Int?,
    val UsageCost: String?,
    val AddressInfo: AddressInfo?,
    val Connections: ArrayList<Connections>,
    val NumberOfPoints: Int?,
    val GeneralComments: String?,
    val DatePlanned: String?,
    val DateLastConfirmed: String?,
    val StatusTypeID: Int?,
    val DateLastStatusUpdate: String?,
    val MetadataValues: String?,
    val DataQualityLevel: Int?,
    val DateCreated: String?,
    val SubmissionStatusTypeID: Int?

)
