package com.project.chargingstationfinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.chargingstationfinder.database.entities.ChargingStation

@Database(
    entities = [ChargingStation::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getChargingStationDao(): ChargingStationDao

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