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
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.model.*
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.repository.MapRepository
import com.project.chargingstationfinder.responses.ChargingStation
import com.project.chargingstationfinder.util.Constant
import com.project.chargingstationfinder.view.MapFragment

class MapViewModel(
    private val repository : MapRepository
) :
    ViewModel(){

    private var _chargingStationsLiveData = MutableLiveData<List<ChargingStation>>()
    val chargingStationLiveData: LiveData<List<ChargingStation>>
        get() = _chargingStationsLiveData

    var generalListener: GeneralListener? = null

    private lateinit var hMap: HuaweiMap
    private lateinit var marker: Marker
    private lateinit var cameraUpdate: CameraUpdate
    private lateinit var cameraPosition: CameraPosition
    private val radius = SharedPreferencesHelper.getInt("radius")
    private val countryCode = SharedPreferencesHelper.getString("countryCode")
    private val latitude = SharedPreferencesHelper.getFloat("latitude")
    private val longitude = SharedPreferencesHelper.getFloat("longitude")

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
            val mapResponse = repository.getChargingStations(
                countryCode,
                latitude,
                longitude,
                radius,
                2,
                Constant.apiKey,
                hMap
            )
            generalListener?.onSuccess("Station Info Collection Successful",mapResponse)
        }catch (e:java.lang.Exception){
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