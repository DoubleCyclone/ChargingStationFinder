package com.project.chargingstationfinder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import com.project.chargingstationfinder.json.ChargingStation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapRepository {

    private lateinit var marker: Marker
    private lateinit var chargingStationList: MutableList<ChargingStation>

    fun getChargingStations(
        countryCode: String,
        latitude: Float,
        longitude: Float,
        distance: Int,
        distanceUnit: Int,
        apiKey: String,
        hMap: HuaweiMap
    ): LiveData<String> {

        val getStationResponse = MutableLiveData<String>()
        ApiClient().getPois(countryCode, latitude, longitude, distance, distanceUnit, apiKey)
            .enqueue(object :
                Callback<List<ChargingStation>> {
                override fun onFailure(call: Call<List<ChargingStation>>, t: Throwable) {
                    getStationResponse.value = t.message
                }

                override fun onResponse(
                    call: Call<List<ChargingStation>>,
                    response: Response<List<ChargingStation>>
                ) {

                    if (response.isSuccessful) {
                        getStationResponse.value = response.body()?.toString()
                        chargingStationList = (response.body() as MutableList<ChargingStation>?)!!
                        chargingStationList.forEach {

                            if (it.StatusType?.IsOperational == true) {
                                marker = hMap.addMarker(
                                    MarkerOptions()
                                        .icon(
                                            BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_GREEN
                                            )
                                        )
                                        .title(it.AddressInfo?.AddressLine1 ?: "unknown")
                                        .position(
                                            LatLng(
                                                it.AddressInfo?.Latitude ?: 0.0,
                                                it.AddressInfo?.Longitude ?: 0.0
                                            )
                                        )
                                )
                            } else {
                                marker = hMap.addMarker(
                                    MarkerOptions()
                                        .icon(
                                            BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_RED
                                            )
                                        )
                                        .title(it.AddressInfo?.AddressLine1 ?: "unknown")
                                        .position(
                                            LatLng(
                                                it.AddressInfo?.Latitude ?: 0.0,
                                                it.AddressInfo?.Longitude ?: 0.0
                                            )
                                        )
                                )
                            }

                        }
                    } else {
                        getStationResponse.value = response.errorBody()?.toString()
                    }
                }
            })

        return getStationResponse
    }
}