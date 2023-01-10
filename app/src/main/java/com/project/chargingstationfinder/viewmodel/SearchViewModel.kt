package com.project.chargingstationfinder.viewmodel

import SharedPreferencesHelper
import android.app.Activity
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.constraintlayout.widget.StateSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.huawei.hms.location.*
import com.huawei.hms.support.api.location.common.HMSLocationLog
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentSearchBinding

class SearchViewModel : ViewModel() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationRequest = LocationRequest()
    private lateinit var mLocationCallback: LocationCallback
    private var radius: Int = 0
    private var countryCode: String = ""

    fun initializeLocationReq(binding: FragmentSearchBinding, activity: Activity) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
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
                        binding.longitudeNumberTv.text = location.longitude.toString()
                        binding.latitudeNumberTv.text = location.latitude.toString()
                    }

                }
            }
        }
    }

    fun requestUpdate(binding: FragmentSearchBinding, activity: Activity) {
        //get the radius from the editText if it is not empty
        if (!binding.editText.text.toString().equals(null) && binding.editText.text.toString() != ""
        ) {
            radius = binding.editText.text.toString().toInt()
        }
        println("$radius")
        //get the selected country code from the spinner
        countryCode = binding.countriesSpinner.selectedItem.toString()

        //fusedLocation stuff
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(activity)
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

    fun removeLocationUpdates(binding: FragmentSearchBinding, fragment: Fragment) {
        try {
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                .addOnSuccessListener {
                    binding.longitudeNumberTv.text = fragment.getString(R.string.zero)
                    binding.latitudeNumberTv.text = fragment.getString(R.string.zero)
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

    fun searchToMap(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.action_searchFragment_to_mapFragment)
    }

    fun putVariables(binding: FragmentSearchBinding) {
        SharedPreferencesHelper.putInt("radius", radius)
        SharedPreferencesHelper.putString("countryCode", countryCode)
        SharedPreferencesHelper.putFloat(
            "latitude",
            binding.latitudeNumberTv.text.toString().toFloat()
        )
        SharedPreferencesHelper.putFloat(
            "longitude",
            binding.longitudeNumberTv.text.toString().toFloat()
        )
    }
}