package com.project.chargingstationfinder.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.chargingstationfinder.database.entities.StatusType

@Dao
interface StatusTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(statusType: StatusType)

    @Query("SELECT * FROM StatusType")
    fun getStatusType(): LiveData<StatusType>
}