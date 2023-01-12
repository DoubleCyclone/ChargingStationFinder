package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentMapBinding
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.repository.ApiClient
import com.project.chargingstationfinder.repository.MapRepository
import com.project.chargingstationfinder.util.*
import com.project.chargingstationfinder.viewmodel.MapViewModel

class MapFragment : Fragment(), OnMapReadyCallback, GeneralListener {

    private lateinit var viewModel: MapViewModel
    lateinit var binding: FragmentMapBinding
    private lateinit var mMapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val api = ApiClient()
        val repository = MapRepository(api)
        val factory = MapViewModelFactory(repository)


        viewModel = ViewModelProvider(this,factory)[MapViewModel::class.java]
        viewModel.generalListener = this
        viewModel.initializeMap(this)
        binding = FragmentMapBinding.inflate(layoutInflater)
        binding.mapViewModel = viewModel
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

        //viewModel.chargingStationLiveData.observe(viewLifecycleOwner) {}

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
        viewModel.onMapReady(huaweiMap)
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

    override fun onStarted(message: String) {
        binding.mapPb.show()
        activity?.toast(message)
    }

    override fun onSuccess(message: String, generalResponse: LiveData<String>?) {
        generalResponse?.observe(this) {
            activity?.toast(it)
            binding.mapPb.hide()
        } ?: run {
            activity?.toast(message)
            binding.mapPb.hide()
        }
    }

    override fun onFailure(message: String) {
        binding.mapPb.hide()
        activity?.toast(message)
    }
}

