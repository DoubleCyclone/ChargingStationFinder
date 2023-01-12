package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huawei.agconnect.auth.AGConnectAuth
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentSearchBinding
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.util.hide
import com.project.chargingstationfinder.util.show
import com.project.chargingstationfinder.util.toast
import com.project.chargingstationfinder.viewmodel.SearchViewModel

class SearchFragment : Fragment(), GeneralListener {

    private lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentSearchBinding
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.searchViewModel = viewModel
        viewModel.generalListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createSpinner(view)
        if (AGConnectAuth.getInstance() != null) {
            binding.welcomeUsertv.text =
                "Welcome " + AGConnectAuth.getInstance().currentUser.displayName + " !"
        }
        viewModel.initializeLocationReq(this)
        setListeners()
    }

    private fun setListeners() {
        binding.toMapbtn.setOnClickListener {
            viewModel.putVariables(this)
            viewModel.searchToMap(this)
        }
        binding.locationBtn.setOnClickListener {
            viewModel.requestUpdate(this)
        }
        binding.clearTv.setOnClickListener {
            viewModel.removeLocationUpdates(this)
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

    override fun onStarted(message: String) {
        activity?.toast(message)
        binding.searchPb.show()
    }

    override fun onSuccess(message: String, generalResponse: LiveData<String>?) {
        binding.searchPb.hide()
        generalResponse?.observe(this, Observer {
            activity?.toast(it)
        }) ?: run {
            activity?.toast(message)
        }
    }

    override fun onFailure(message: String) {
        binding.searchPb.hide()
        activity?.toast(message)
    }

}