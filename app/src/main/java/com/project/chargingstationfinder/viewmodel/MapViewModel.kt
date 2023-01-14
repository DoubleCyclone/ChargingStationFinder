package com.project.chargingstationfinder.viewmodel

import android.util.Log
import androidx.constraintlayout.widget.StateSet
import androidx.lifecycle.ViewModel
import com.huawei.hms.maps.CameraUpdate
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.model.*
import com.project.chargingstationfinder.database.entities.ChargingStation
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.repository.MapRepository
import com.project.chargingstationfinder.util.*
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

    val chargingStations by lazyDeferred {
        repository.getChargingStations()
    }

    fun initializeMap(view: MapFragment) {
        // Initialize the SDK.
        MapsInitializer.setApiKey(Constant.apiKey)
        MapsInitializer.initialize(view.activity)
    }

    private fun getStations() {

        generalListener?.onStarted("Charging Station Info Collection Started")
        if (countryCode.isEmpty() || latitude.isNaN() || longitude.isNaN()) {
            generalListener?.onFailure("Country code or Location is Invalid")
            return
        }
        Coroutines.main {
            try {
                val mapResponse = repository.getChargingStations(
                    countryCode,
                    latitude,
                    longitude,
                    radius,
                    2,
                    Constant.apiKey
                )
                mapResponse.forEach {
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
                        //repository.saveChargingStation(it)
                    }
                    repository.getChargingStations()
                    println(it.Connections.toString())
                    println(it.AddressInfo?.AddressLine1.toString() + it.AddressInfo?.AddressLine2.toString())
                }
                generalListener?.onSuccess("Charging Station Info Collection Success", null)
            } catch (e: ApiException) {
                generalListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                generalListener?.onFailure(e.message!!)
            }
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