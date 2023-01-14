package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.huawei.agconnect.auth.AGConnectAuth
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentSearchBinding
import com.project.chargingstationfinder.factory.SearchViewModelFactory
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.util.hide
import com.project.chargingstationfinder.util.show
import com.project.chargingstationfinder.util.toast
import com.project.chargingstationfinder.viewmodel.SearchViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SearchFragment : Fragment(), GeneralListener, KodeinAware {

    override val kodein by kodein()
    private val factory: SearchViewModelFactory by instance()

    private lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentSearchBinding
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this,factory)[SearchViewModel::class.java]
        viewModel.generalListener = this
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this
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
        generalResponse?.observe(this) {
            activity?.toast(it)
        } ?: run {
            activity?.toast(message)
        }
    }

    override fun onFailure(message: String) {
        binding.searchPb.hide()
        activity?.toast(message)
    }

}