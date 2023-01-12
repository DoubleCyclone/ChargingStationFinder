package com.project.chargingstationfinder.json

import com.google.gson.annotations.SerializedName


data class ChargingStation(

    @SerializedName("DataProvider") var DataProvider: DataProvider? = DataProvider(),
    @SerializedName("OperatorInfo") var OperatorInfo: OperatorInfo? = OperatorInfo(),
    @SerializedName("UsageType") var UsageType: UsageType? = UsageType(),
    @SerializedName("StatusType") var StatusType: StatusType? = StatusType(),
    @SerializedName("SubmissionStatus") var SubmissionStatus: SubmissionStatus? = SubmissionStatus(),
    //@SerializedName("UserComments"           ) var UserComments           : String?                = null,
    @SerializedName("PercentageSimilarity") var PercentageSimilarity: String? = null,
    //@SerializedName("MediaItems"             ) var MediaItems             : String?                = null,
    //@SerializedName("IsRecentlyVerified"     ) var IsRecentlyVerified     : Boolean?               = null,
    //@SerializedName("DateLastVerified"       ) var DateLastVerified       : String?                = null,
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("UUID") var UUID: String? = null,
    //@SerializedName("ParentChargePointID"    ) var ParentChargePointID    : String?                = null,
    //@SerializedName("DataProviderID"         ) var DataProviderID         : Int?                   = null,
    //@SerializedName("DataProvidersReference" ) var DataProvidersReference : String?                = null,
    //@SerializedName("OperatorID"             ) var OperatorID             : Int?                   = null,
    //@SerializedName("OperatorsReference"     ) var OperatorsReference     : String?                = null,
    //@SerializedName("UsageTypeID"            ) var UsageTypeID            : Int?                   = null,
    //@SerializedName("UsageCost"              ) var UsageCost              : String?                = null,
    @SerializedName("AddressInfo") var AddressInfo: AddressInfo? = AddressInfo(),
    @SerializedName("Connections") var Connections: ArrayList<Connections> = arrayListOf(),
    @SerializedName("NumberOfPoints") var NumberOfPoints: Int? = null,
    //@SerializedName("GeneralComments"        ) var GeneralComments        : String?                = null,
    //@SerializedName("DatePlanned"            ) var DatePlanned            : String?                = null,
    //@SerializedName("DateLastConfirmed"      ) var DateLastConfirmed      : String?                = null,
    //@SerializedName("StatusTypeID"           ) var StatusTypeID           : Int?                   = null,
    //@SerializedName("DateLastStatusUpdate"   ) var DateLastStatusUpdate   : String?                = null,
    //@SerializedName("MetadataValues"         ) var MetadataValues         : String?                = null,
    //@SerializedName("DataQualityLevel"       ) var DataQualityLevel       : Int?                   = null,
    //@SerializedName("DateCreated"            ) var DateCreated            : String?                = null,
    //@SerializedName("SubmissionStatusTypeID" ) var SubmissionStatusTypeID : Int?

)
