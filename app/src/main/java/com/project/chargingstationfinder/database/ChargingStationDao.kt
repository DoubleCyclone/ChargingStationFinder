package com.project.chargingstationfinder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.chargingstationfinder.database.entities.ChargingStation

@Dao
interface ChargingStationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chargingStation: ChargingStation)

    @Query("SELECT * FROM ChargingStation")
    fun getChargingStation() : LiveData<ChargingStation>


}