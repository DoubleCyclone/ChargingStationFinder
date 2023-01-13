package com.project.chargingstationfinder.viewmodel

import android.util.Log
import androidx.constraintlayout.widget.StateSet
import androidx.lifecycle.ViewModel
import com.huawei.hms.maps.CameraUpdate
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.model.*
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.repository.MapRepository
import com.project.chargingstationfinder.util.Constant
import com.project.chargingstationfinder.util.Coroutines
import com.project.chargingstationfinder.util.PreferenceProvider
import com.project.chargingstationfinder.view.MapFragment

class MapViewModel(
    private val repository: MapRepository,
    prefs: PreferenceProvider
) :
    ViewModel() {

    var generalListener: GeneralListener? = null

    private lateinit var hMap: HuaweiMap
    private lateinit var marker: Marker
    private lateinit var cameraUpdate: CameraUpdate
    private lateinit var cameraPosition: CameraPosition
    private val radius = prefs.getInt("radius")
    private val countryCode = prefs.getString("countryCode")
    private val latitude = prefs.getFloat("latitude")
    private val longitude = prefs.getFloat("longitude")

    fun initializeMap(view: MapFragment) {
        // Initialize the SDK.
        MapsInitializer.setApiKey(Constant.apiKey)
        MapsInitializer.initialize(view.activity)
    }

    private fun getStations() {
        try {
            generalListener?.onStarted("Station Info Collection Started")
            if (countryCode.isEmpty() || latitude.isNaN() || longitude.isNaN()) {
                generalListener?.onFailure("Country code or Location is Invalid")
                return
            }
            Coroutines.main {
                val response = repository.getChargingStations(
                    countryCode,
                    latitude,
                    longitude,
                    radius,
                    2,
                    Constant.apiKey
                )
                if (response.isSuccessful) {
                    val chargingStationList =
                        (response.body())!! // @TODO USES THE ONE IN THE JSON NOT THE ENTITY
                    chargingStationList.forEach {
                        if (it.StatusType?.IsOperational != null) {
                            marker = hMap.addMarker(
                                MarkerOptions()
                                    .icon(
                                        BitmapDescriptorFactory.defaultMarker(
                                            if (it.StatusType?.IsOperational!!)
                                                BitmapDescriptorFactory.HUE_GREEN else BitmapDescriptorFactory.HUE_RED
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
                    generalListener?.onSuccess(response.body().toString(), null)
                } else {
                    generalListener?.onFailure("Error Code : ${response.code()}")
                }
            }

        } catch (e: java.lang.Exception) {
            generalListener?.onFailure(e.message!!)
        }

    }

    fun onMapReady(huaweiMap: HuaweiMap) {
        Log.d(StateSet.TAG, "onMapReady: ")
        hMap = huaweiMap

        marker = huaweiMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker()) // default marker
                .title("Your Location") // maker title
                .position(
                    LatLng(
                        latitude.toDouble(),
                        longitude.toDouble()
                    )
                )
        )
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
        getStations()
    }
}