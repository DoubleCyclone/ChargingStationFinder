package com.project.chargingstationfinder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.chargingstationfinder.database.entities.AddressInfo

@Dao
interface AddressInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addressInfo: AddressInfo)

    @Query("SELECT * FROM AddressInfo")
    fun getAddressInfo(): LiveData<AddressInfo>
}