package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.database.entities.ChargingStation
import com.project.chargingstationfinder.databinding.FragmentDetailsBinding
import com.project.chargingstationfinder.factory.MapViewModelFactory
import com.project.chargingstationfinder.util.ChargingStationItem
import com.project.chargingstationfinder.util.Coroutines
import com.project.chargingstationfinder.util.hide
import com.project.chargingstationfinder.util.show
import com.project.chargingstationfinder.viewmodel.MapViewModel
import com.xwray.groupie.GroupAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailsFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: MapViewModelFactory by instance()

    private lateinit var viewModel: MapViewModel
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this, factory)[MapViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
        setListeners()
    }

    private fun bindUI() = Coroutines.main{
        binding.detailsPb.show()

        viewModel.chargingStations.await().observe(viewLifecycleOwner) {
            binding.detailsPb.hide()
            initRecyclerView(it.toChargingStationItem())
        }
    }

    private fun initRecyclerView(chargingStationItem: List<ChargingStationItem>) {
        val mAdapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>().apply {
            addAll(chargingStationItem)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<ChargingStation>.toChargingStationItem(): List<ChargingStationItem> {
        return this.map {
            ChargingStationItem(it)
        }
    }

    private fun setListeners() {
    }

    private fun detailsToMap() {
        findNavController().navigate(R.id.action_detailsFragment_to_mapFragment)
    }
}