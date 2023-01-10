package com.project.chargingstationfinder.viewmodel

import SharedPreferencesHelper
import android.util.Log
import androidx.constraintlayout.widget.StateSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huawei.hms.maps.CameraUpdate
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.*
import com.project.chargingstationfinder.misc.Constant
import com.project.chargingstationfinder.model.ApiClient
import com.project.chargingstationfinder.model.ChargingStation
import com.project.chargingstationfinder.model.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel(private val repository: Repository = Repository(ApiClient.getClient())) :
    ViewModel() {

    private var _chargingStationsLiveData = MutableLiveData<List<ChargingStation>>()
    val chargingStationLiveData: LiveData<List<ChargingStation>>
        get() = _chargingStationsLiveData

    private lateinit var hMap: HuaweiMap
    private lateinit var marker: Marker
    private lateinit var chargingStationList: MutableList<ChargingStation>
    private lateinit var cameraUpdate: CameraUpdate
    private lateinit var cameraPosition: CameraPosition
    private val radius = SharedPreferencesHelper.getInt("radius")
    private val countryCode = SharedPreferencesHelper.getString("countryCode")
    private val latitude = SharedPreferencesHelper.getFloat("latitude")
    private val longitude = SharedPreferencesHelper.getFloat("longitude")

    init {
        fetchChargingStation()
    }

    private fun fetchChargingStation() {

        val client = repository.getChargingStations(
            countryCode,
            latitude,
            longitude,
            radius,
            2,
            Constant.apiKey
        )

        client.enqueue(object : Callback<List<ChargingStation>> {
            override fun onFailure(call: Call<List<ChargingStation>>, t: Throwable) {
                //Toast.makeText(context, t.message.toString(), Toast.LENGTH_LONG).show()
                Log.d("Failure", t.message.toString())
                println(t.message.toString())
            }

            override fun onResponse(
                call: Call<List<ChargingStation>>,
                response: Response<List<ChargingStation>>
            ) {

                if (response.isSuccessful) {
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
                }
            }
        })
    }

    fun onMapReady(huaweiMap: HuaweiMap) {
        Log.d(StateSet.TAG, "onMapReady: ")
        hMap = huaweiMap

        // marker add
        marker = huaweiMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker()) // default marker
                .title("Your Location") // maker title
                .position(
                    LatLng(
                        latitude.toDouble(),
                        longitude.toDouble()
                    )
                ) // marker position

        )
        // camera position settings
        cameraPosition = CameraPosition.builder()
            .target(
                LatLng(
                    latitude.toDouble(),
                    longitude.toDouble()
                )
            )
            .zoom(10f)
            .bearing(2.0f)
            .tilt(2.5f).build()
        cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        huaweiMap.moveCamera(cameraUpdate)
    }
}