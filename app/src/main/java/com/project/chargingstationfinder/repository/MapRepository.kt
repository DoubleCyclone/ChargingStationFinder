package com.project.chargingstationfinder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import com.project.chargingstationfinder.database.AppDatabase
import com.project.chargingstationfinder.database.entities.ChargingStation
import com.project.chargingstationfinder.network.ApiClient
import com.project.chargingstationfinder.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.time.LocalDateTime

class MapRepository(
    private val api: ApiClient,
    private val db: AppDatabase
) : SafeApiRequest() {

    private val chargingStations = MutableLiveData<List<ChargingStation>>()

    suspend fun getChargingStations(
        countryCode: String,
        latitude: Float,
        longitude: Float,
        distance: Int,
        distanceUnit: Int,
        apiKey: String,
    ): List<ChargingStation> {

        return apiRequest {
            api.getPois(countryCode, latitude, longitude, distance, distanceUnit, apiKey)
        }
    }

    suspend fun saveChargingStation(chargingStation: ChargingStation) =
        db.getChargingStationDao().insert(chargingStation)


    suspend fun getChargingStations(): LiveData<List<ChargingStation>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getChargingStationDao().getChargingStations()
        }
    }

    private suspend fun fetchQuotes() {
        try {
            val response = apiRequest { api.getChargingStations() }
            chargingStations.postValue(response.chargingStations)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}