package com.project.chargingstationfinder.database

import android.content.Context
import androidx.room.*
import com.project.chargingstationfinder.database.dao.AddressInfoDao
import com.project.chargingstationfinder.database.dao.ChargingStationDao
import com.project.chargingstationfinder.database.dao.ConnectionsDao
import com.project.chargingstationfinder.database.dao.StatusTypeDao
import com.project.chargingstationfinder.database.entities.AddressInfo
import com.project.chargingstationfinder.database.entities.ChargingStation
import com.project.chargingstationfinder.database.entities.Connections
import com.project.chargingstationfinder.database.entities.StatusType
import com.project.chargingstationfinder.util.Converter

@Database(
    entities = [ChargingStation::class, Connections::class, StatusType::class, AddressInfo::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getChargingStationDao(): ChargingStationDao
    abstract fun getConnectionsDao(): ConnectionsDao
    abstract fun getStatusType(): StatusTypeDao
    abstract fun getAddressInfo(): AddressInfoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }

        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MyDatabase.db"
        ).build()
    }
}