package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.huawei.agconnect.auth.AGConnectAuth
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentSearchBinding
import com.project.chargingstationfinder.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    lateinit var binding: FragmentSearchBinding
    private lateinit var spinner: Spinner

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

        //searchViewModel.initializeLocationReq(binding, activity as Activity)
        searchViewModel.initializeLocationReq(this)
        setListeners()
    }

    private fun setListeners() {
        binding.toMapbtn.setOnClickListener {
            //searchViewModel.putVariables(binding)
            searchViewModel.putVariables(this)
            searchViewModel.searchToMap(this)
        }
        binding.locationBtn.setOnClickListener {
           // searchViewModel.requestUpdate(binding, requireActivity())
            searchViewModel.requestUpdate(this)
        }
        binding.clearTv.setOnClickListener {
            //searchViewModel.removeLocationUpdates(binding, this)
            searchViewModel.removeLocationUpdates(this)
        }
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

}