package com.project.chargingstationfinder.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.chargingstationfinder.database.entities.ChargingStation

@Dao
interface ChargingStationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chargingStation: ChargingStation)

    @Query("SELECT * FROM ChargingStation")
    fun getChargingStation() : LiveData<ChargingStation>

}