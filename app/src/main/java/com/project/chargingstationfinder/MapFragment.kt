package com.project.chargingstationfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.project.chargingstationfinder.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    //Fragment onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("r") { key, bundle ->
            // Any type can be passed via to the bundle
            val radius = bundle.getInt("radius")
            println(radius)
        }
        setFragmentResultListener("c") { key, bundle ->
            val countryCode = bundle.getString("countryCode").toString()
            println(countryCode)
        }
        setFragmentResultListener("la") {key, bundle ->
            val latitude = bundle.getFloat("latitude")
            println(latitude)
        }
        setFragmentResultListener("lo") {key, bundle ->
            val longitude = bundle.getFloat("longitude")
            println(longitude)
        }
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
        setListeners()
    }

    private fun setListeners() {
        binding.toDetailsbtn.setOnClickListener {
            mapToDetails()
        }
    }

    private fun mapToDetails() {
        findNavController().navigate(R.id.action_mapFragment_to_detailsFragment)
    }

}