package com.project.chargingstationfinder.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.project.chargingstationfinder.database.entities.AddressInfo
import com.project.chargingstationfinder.database.entities.StatusType

@ProvidedTypeConverter
class Converter {

    @TypeConverter
    fun fromStatusType(statusType: StatusType) : String = Gson().toJson(statusType)
    @TypeConverter
    fun toStatusType(string: String) : StatusType = Gson().fromJson(string, StatusType::class.java)
    @TypeConverter
    fun fromAddressInfo(addressInfo: AddressInfo) : String = Gson().toJson(addressInfo)
    @TypeConverter
    fun toAddressInfo(string: String) : AddressInfo = Gson().fromJson(string, AddressInfo::class.java)
}