package com.project.chargingstationfinder.viewmodel

import SharedPreferencesHelper
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.constraintlayout.widget.StateSet
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.huawei.hms.location.*
import com.huawei.hms.support.api.location.common.HMSLocationLog
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.view.SearchFragment

class SearchViewModel : ViewModel() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationRequest = LocationRequest()
    private lateinit var mLocationCallback: LocationCallback
    var radius: Int = 0
    private var countryCode: String = ""

    fun initializeLocationReq(view : SearchFragment) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.activity)
        mLocationRequest.interval = 10000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val locations: List<Location> = locationResult.locations
                if (locations.isNotEmpty()) {
                    for (location in locations) {
                        HMSLocationLog.i(
                            StateSet.TAG,
                            "onLocationResult location[Longitude,Latitude,Accuracy]:" + location.longitude + "," + location.latitude + "," + location.accuracy,
                            null
                        )
                        view.binding.longitudeNumberTv.text = location.longitude.toString()
                        view.binding.latitudeNumberTv.text = location.latitude.toString()
                    }

                }
            }
        }
    }

    fun requestUpdate(view : SearchFragment) {
        //get the radius from the editText if it is not empty
        if (!view.binding.editText.text.toString().equals(null) && view.binding.editText.text.toString() != ""
        ) {
            radius = view.binding.editText.text.toString().toInt()
        }
        println("$radius")
        //get the selected country code from the spinner
        countryCode = view.binding.countriesSpinner.selectedItem.toString()

        //fusedLocation stuff
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(view.activity)
        val builder = LocationSettingsRequest.Builder()
        mLocationRequest = LocationRequest()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()
        // Check the device location settings.
        settingsClient.checkLocationSettings(locationSettingsRequest)
            // Define the listener for success in calling the API for checking device location settings.
            .addOnSuccessListener { locationSettingsResponse ->
                val locationSettingsStates = locationSettingsResponse.locationSettingsStates
                val stringBuilder = StringBuilder()
                // Check whether the location function is enabled.
                stringBuilder.append("isLocationUsable=")
                    .append(locationSettingsStates.isLocationUsable)
                // Check whether HMS Core (APK) is available.
                stringBuilder.append(",\nisHMSLocationUsable=")
                    .append(locationSettingsStates.isHMSLocationUsable)
                Log.i(StateSet.TAG, "checkLocationSetting onComplete:$stringBuilder")

                fusedLocationProviderClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    Looper.getMainLooper()
                )
                    .addOnSuccessListener {
                        Log.i(StateSet.TAG, "requestLocationUpdatesWithCallback onSuccess")
                    }
                    .addOnFailureListener {
                        Log.e(
                            StateSet.TAG,
                            "requestLocationUpdatesWithCallback onFailure:" + it.message
                        )
                    }
            }
            // Define callback for failure in checking the device location settings.
            .addOnFailureListener { e ->
                Log.i(StateSet.TAG, "checkLocationSetting onFailure:" + e.message)
            }


    }

    fun removeLocationUpdates(view :SearchFragment) {
        try {
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                .addOnSuccessListener {
                    view.binding.longitudeNumberTv.text = view.getString(R.string.zero)
                    view.binding.latitudeNumberTv.text = view.getString(R.string.zero)
                    HMSLocationLog.i(
                        StateSet.TAG,
                        "removeLocationUpdatesWithCallback onSuccess",
                        null
                    )
                }.addOnFailureListener {
                    HMSLocationLog.i(
                        StateSet.TAG,
                        "removeLocationUpdatesWithCallback onFailure: " + it.message,
                        null
                    )
                }
        } catch (e: java.lang.Exception) {
            HMSLocationLog.e(
                StateSet.TAG,
                "removeLocationUpdatesWithCallback exception" + e.message,
                null
            )
        }
    }

    fun searchToMap(view: SearchFragment) {
        view.findNavController().navigate(R.id.action_searchFragment_to_mapFragment)
    }

    fun putVariables(view: SearchFragment) {
        SharedPreferencesHelper.putInt("radius", radius)
        SharedPreferencesHelper.putString("countryCode", countryCode)
        SharedPreferencesHelper.putFloat(
            "latitude",
            view.binding.latitudeNumberTv.text.toString().toFloat()
        )
        SharedPreferencesHelper.putFloat(
            "longitude",
            view.binding.longitudeNumberTv.text.toString().toFloat()
        )
    }
}