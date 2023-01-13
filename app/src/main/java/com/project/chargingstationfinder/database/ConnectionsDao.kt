package com.project.chargingstationfinder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.chargingstationfinder.database.entities.Connections

@Dao
interface ConnectionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(connections: Connections)

    @Query("SELECT * FROM Connections")
    fun getConnections() : LiveData<Connections>
}