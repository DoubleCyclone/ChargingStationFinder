package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hdsturkey.yalovabsm404.utils.SharedPreferencesHelper
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.*
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentMapBinding
import com.project.chargingstationfinder.model.*
import com.project.chargingstationfinder.viewmodel.MapViewModel


class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by lazy {
        ViewModelProvider(this).get(MapViewModel::class.java)
    }

    private lateinit var binding: FragmentMapBinding
    private lateinit var hMap: HuaweiMap
    private lateinit var mMapView: MapView

    private lateinit var marker: Marker
    private lateinit var cameraUpdate: CameraUpdate
    private lateinit var cameraPosition: CameraPosition

    lateinit var apiService: ApiService
    lateinit var chargingStationList: MutableList<ChargingStation>

    //Fragment onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        radius = SharedPreferencesHelper.getInt("radius")
        countryCode = SharedPreferencesHelper.getString("countryCode")
        latitude = SharedPreferencesHelper.getFloat("latitude")
        longitude = SharedPreferencesHelper.getFloat("longitude")

        println("$radius , $countryCode , $latitude , $longitude , amogus")

        // Initialize the SDK.
        MapsInitializer.setApiKey(MAPVIEW_BUNDLE_KEY)
        MapsInitializer.initialize(activity)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMapView = binding.mapviewMapviewdemo
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)

        viewModel.chargingStationLiveData.observe(viewLifecycleOwner) {}

        /*apiService = ApiClient.getClient()!!
        val client = apiService.getPois(
            "TR",
            41.031261,
            29.117277,
            10,
            2,
            Constant.apiKey
        )

        client.enqueue(object : Callback<List<ChargingStation>> {
            override fun onFailure(call: Call<List<ChargingStation>>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_LONG).show()
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
                                    .title(it.AddressInfo?.AddressLine1 ?: "empty")
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
                                    .title(it.AddressInfo?.AddressLine1 ?: "empty")
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
        })*/

        setListeners()
    }

    private fun setListeners() {
    }

    private fun mapToDetails() {
        findNavController().navigate(R.id.action_mapFragment_to_detailsFragment)
    }

    override fun onMapReady(huaweiMap: HuaweiMap) {
        /*Log.d(TAG, "onMapReady: ")
        hMap = huaweiMap

        // marker add
        marker = huaweiMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker()) // default marker
                .title("Huawei Turkey") // maker title
                .position(LatLng(latitude.toDouble(), longitude.toDouble())) // marker position

        )
        // camera position settings
        cameraPosition = CameraPosition.builder()
            .target(LatLng(latitude.toDouble(), longitude.toDouble())) //41.031261, 29.117277
            .zoom(10f)
            .bearing(2.0f)
            .tilt(2.5f).build()
        cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        huaweiMap.moveCamera(cameraUpdate)*/
        viewModel.onMapReady(huaweiMap)

    }

    companion object {
        var radius: Int = 0
        var countryCode: String = ""
        var latitude: Float = 0F
        var longitude: Float = 0F

        private const val MAPVIEW_BUNDLE_KEY =
            "DAEDACvUF0gNnfPjNQIE3uC70aAZNtSAQGomaz+R1X9ZP6w37Slh6GDIlm+sV/ag3MOzyeWpT8thAEYVuGv8xhUWyMIXvgJV5pDM1Q=="
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle: Bundle? = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }

        mMapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
}

