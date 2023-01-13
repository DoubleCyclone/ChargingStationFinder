package com.project.chargingstationfinder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import com.project.chargingstationfinder.database.entities.ChargingStation
import com.project.chargingstationfinder.network.ApiClient
import com.project.chargingstationfinder.network.SafeApiRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapRepository(
    private val api: ApiClient
) : SafeApiRequest() {

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
}