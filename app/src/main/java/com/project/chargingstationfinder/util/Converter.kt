package com.project.chargingstationfinder.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.project.chargingstationfinder.database.entities.AddressInfo
import com.project.chargingstationfinder.database.entities.Connections
import com.project.chargingstationfinder.database.entities.StatusType

class Converter {

    @TypeConverter
    fun fromStatusType(statusType: StatusType) : String = Gson().toJson(statusType)
    @TypeConverter
    fun toStatusType(string: String) : StatusType = Gson().fromJson(string, StatusType::class.java)
    @TypeConverter
    fun fromAddressInfo(addressInfo: AddressInfo) : String = Gson().toJson(addressInfo)
    @TypeConverter
    fun toAddressInfo(string: String) : AddressInfo = Gson().fromJson(string, AddressInfo::class.java)
    @TypeConverter
    fun fromConnections(connections: Connections) : String = Gson().toJson(connections)
    @TypeConverter
    fun toConnections(string: String) : Connections = Gson().fromJson(string, Connections::class.java)
    @TypeConverter
    fun fromConnectionsAList(connectionsAList: ArrayList<Connections>) : String = Gson().toJson(connectionsAList)
    @TypeConverter
    fun toConnectionsAList(string: String) : ArrayList<Connections> = Gson().fromJson(string, ArrayList<Connections>()::class.java)
}