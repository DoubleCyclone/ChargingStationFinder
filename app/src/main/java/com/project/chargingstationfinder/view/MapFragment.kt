package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentMapBinding
import com.project.chargingstationfinder.factory.MapViewModelFactory
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.util.*
import com.project.chargingstationfinder.viewmodel.MapViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MapFragment : Fragment(), OnMapReadyCallback, GeneralListener, KodeinAware {

    override val kodein by kodein()
    private val factory: MapViewModelFactory by instance()

    private lateinit var viewModel: MapViewModel
    lateinit var binding: FragmentMapBinding
    private lateinit var mMapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this, factory)[MapViewModel::class.java]
        viewModel.generalListener = this
        viewModel.initializeMap(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.mapViewModel = viewModel
        binding.lifecycleOwner = this
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

        setListeners()
    }

    private fun setListeners() {
        binding.toDetailsBtn.setOnClickListener {
            mapToDetails()
        }
    }

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

