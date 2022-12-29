package com.project.chargingstationfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.chargingstationfinder.databinding.FragmentMapBinding
import com.project.chargingstationfinder.databinding.FragmentSearchBinding

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.toDetailsbtn.setOnClickListener{
            mapToDetails()
        }
    }

    private fun mapToDetails() {
        findNavController().navigate(R.id.action_mapFragment_to_detailsFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}