package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.huawei.hms.maps.*
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentMapBinding
import com.project.chargingstationfinder.misc.Constant
import com.project.chargingstationfinder.viewmodel.MapViewModel


class MapFragment : Fragment(), OnMapReadyCallback {

    private val mapViewModel: MapViewModel by lazy {
        ViewModelProvider(this)[MapViewModel::class.java]
    }

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMapView: MapView

    //Fragment onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.initializeMap(this)
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

        mMapView = binding.mapViewHuawei
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constant.apiKey)
        }
        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)

        mapViewModel.chargingStationLiveData.observe(viewLifecycleOwner) {}

        setListeners()
    }
    // TODO: Not yet implemented
    private fun setListeners() {
    }

    // TODO: Not yet implemented 
    private fun mapToDetails() {
        findNavController().navigate(R.id.action_mapFragment_to_detailsFragment)
    }

    override fun onMapReady(huaweiMap: HuaweiMap) {
        mapViewModel.onMapReady(huaweiMap)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle: Bundle? = outState.getBundle(Constant.apiKey)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(Constant.apiKey, mapViewBundle)
        }

        mMapView.onSaveInstanceState(mapViewBundle)
    }
}

