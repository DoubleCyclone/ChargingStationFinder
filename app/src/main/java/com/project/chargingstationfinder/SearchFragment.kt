package com.project.chargingstationfinder

import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
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
    private var mLocationRequest = LocationRequest()
    private lateinit var mLocationCallback: LocationCallback
    private var radius: Int = 0
    private var countryCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createSpinner(view)
        if (AGConnectAuth.getInstance() != null) {
            binding.welcomeUsertv.text =
                "Welcome " + AGConnectAuth.getInstance().currentUser.displayName + " !"
        }

        initializeLocationReq()

        setListeners()
    }

    private fun setListeners() {
        binding.toMapbtn.setOnClickListener {
            setFragmentResult("r", bundleOf("radius" to radius))
            setFragmentResult("c", bundleOf("countryCode" to countryCode))
            setFragmentResult("la", bundleOf("latitude" to binding.latitudeNumberTv.text.toString().toFloat()))
            setFragmentResult("lo", bundleOf("longitude" to binding.longitudeNumberTv.text.toString().toFloat()))
            searchToMap()
        }
        binding.locationBtn.setOnClickListener {
            requestUpdate()
        }
        binding.clearTv.setOnClickListener{
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

    private fun initializeLocationReq() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        mLocationRequest.interval = 10000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val locations: List<Location> = locationResult.locations
                if (locations.isNotEmpty()) {
                    for (location in locations) {
                        HMSLocationLog.i(
                            TAG,
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

    private fun requestUpdate() {
        //get the radius from the editText if it is not empty
        if (!binding.editText.text.toString().equals(null) && binding.editText.text.toString() != ""
        ) {
            radius = binding.editText.text.toString().toInt()
        }
        println("$radius")
        //get the selected country code from the spinner
        countryCode = binding.countriesSpinner.selectedItem.toString()

        //fusedLocation stuff
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(requireActivity())
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
                Log.i(TAG, "checkLocationSetting onComplete:$stringBuilder")

                fusedLocationProviderClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    Looper.getMainLooper()
                )
                    .addOnSuccessListener {
                        Log.i(TAG, "requestLocationUpdatesWithCallback onSuccess")
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "requestLocationUpdatesWithCallback onFailure:" + it.message);
                    }
            }
            // Define callback for failure in checking the device location settings.
            .addOnFailureListener(OnFailureListener { e ->
                Log.i(TAG, "checkLocationSetting onFailure:" + e.message)
            })


    }

    private fun removeLocationUpdates() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                .addOnSuccessListener {
                    binding.longitudeNumberTv.text = getString(R.string.zero)
                    binding.latitudeNumberTv.text = getString(R.string.zero)
                    HMSLocationLog.i(TAG, "removeLocationUpdatesWithCallback onSuccess", null)
                }.addOnFailureListener {
                    HMSLocationLog.i(
                        TAG,
                        "removeLocationUpdatesWithCallback onFailure: " + it.message,
                        null
                    )
                }
        } catch (e: java.lang.Exception) {
            HMSLocationLog.e(TAG, "removeLocationUpdatesWithCallback exception" + e.message, null)
        }
    }

}