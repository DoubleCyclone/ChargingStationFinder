package com.project.chargingstationfinder

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.constraintlayout.widget.StateSet
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.hmf.tasks.OnFailureListener
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.location.*
import com.huawei.hms.support.api.location.common.HMSLocationLog
import com.project.chargingstationfinder.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var spinner: Spinner

    //location kit
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val mLocationRequest = LocationRequest()
    private lateinit var mLocationCallback: LocationCallback
    private val settingsClient : SettingsClient = LocationServices.getSettingsClient(context)
    //private lateinit var settingsClient : SettingsClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        mLocationRequest.interval = 10000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY



        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult != null) {
                    var locations: List<Location> = locationResult.locations
                    if (locations.isNotEmpty()) {
                        for (location in locations) {
                            HMSLocationLog.i(
                                TAG,
                                "onLocationResult location[Longitude,Latitude,Accuracy]:" + location.longitude + "," + location.latitude + "," + location.accuracy,
                                ""
                            )
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createSpinner(view)
        if (AGConnectAuth.getInstance() != null) {
            binding.welcomeUsertv.text =
                "Welcome " + AGConnectAuth.getInstance().currentUser.displayName + " !"
        }


        setListeners()
    }

    private fun setListeners() {
        binding.toMapbtn.setOnClickListener {
            //searchToMap()
            requestLocationUpdates()
        }
        binding.clearBtn.setOnClickListener {
            removeLocationUpdates()
        }
    }

    private fun searchToMap() {
        findNavController().navigate(R.id.action_searchFragment_to_mapFragment)
    }

    private fun createSpinner(view: View) {
        spinner = view.findViewById(R.id.countriesSpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }


    private fun requestLocationUpdates() {
        try {
            val builder = LocationSettingsRequest.Builder()
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
                    Log.i(TAG, "checkLocationSetting onComplete:$stringBuilder")
                }
                // Define callback for failure in checking the device location settings.
                .addOnFailureListener(OnFailureListener { e ->
                    Log.i(TAG, "checkLocationSetting onFailure:" + e.message)
                })
            /*var builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(mLocationRequest)
            var locationSettingsRequest: LocationSettingsRequest = builder.build()

            settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener {
                    Log.i(TAG, "check location settings success")
                    fusedLocationProviderClient.requestLocationUpdates(
                        mLocationRequest, mLocationCallback,
                        Looper.getMainLooper()
                    ).addOnSuccessListener {
                        HMSLocationLog.i(TAG, "requestLocationUpdatesWithCallback onSuccess", "")
                    }.addOnFailureListener {
                        Log.e(TAG, "requestLocationUpdatesWithCallback onFailure:" + it.message)
                    }
                }
                // Define callback for failure in checking the device location settings.
                .addOnFailureListener {
                    Log.e(TAG, "checkLocationSetting onFailure:" + it.message)
                }*/
        } catch (e: java.lang.Exception) {
            HMSLocationLog.e(TAG, "requestLocationUpdatesWithCallback exception" + e.message, "")
        }
        /*fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
            ?.addOnSuccessListener {
                Toast.makeText(
                    activity,
                    "Successfully requested the location update",
                    Toast.LENGTH_LONG
                ).show()
                println("success")
            }
            ?.addOnFailureListener {
                Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG).show()
                println("fail")
            }*/
    }

    private fun removeLocationUpdates() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                .addOnSuccessListener {
                    HMSLocationLog.i(TAG, "removeLocationUpdatesWithCallback onSuccess", "")
                }.addOnFailureListener {
                    HMSLocationLog.i(
                        TAG,
                        "removeLocationUpdatesWithCallback onFailure: " + it.message,
                        ""
                    )
                }
        } catch (e: java.lang.Exception) {
            HMSLocationLog.e(TAG, "removeLocationUpdatesWithCallback exception" + e.message, "")
        }

        /*fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
            .addOnSuccessListener {
                Toast.makeText(
                    activity,
                    "Successfully removed the location update",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    activity,
                    it.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }*/
    }

}