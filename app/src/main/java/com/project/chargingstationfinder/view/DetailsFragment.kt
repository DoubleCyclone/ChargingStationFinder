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
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.FragmentDetailsBinding
import com.project.chargingstationfinder.factory.SearchViewModelFactory
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.viewmodel.DetailsViewModel
import com.project.chargingstationfinder.viewmodel.SearchViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailsFragment : Fragment(), GeneralListener, KodeinAware {

    override val kodein by kodein()
    private val factory: SearchViewModelFactory by instance()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this,factory)[DetailsViewModel::class.java]
        viewModel.generalListener = this
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.toMapbtn2.setOnClickListener{
            detailsToMap()
        }
    }

    private fun detailsToMap() {
        findNavController().navigate(R.id.action_detailsFragment_to_mapFragment)
    }

    override fun onStarted(message: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(message: String, generalResponse: LiveData<String>?) {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
    }
}