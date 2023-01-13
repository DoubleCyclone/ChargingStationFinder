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
import com.project.chargingstationfinder.databinding.FragmentDetailsBinding
import com.project.chargingstationfinder.factory.DetailsViewModelFactory
import com.project.chargingstationfinder.database.entities.Connections
import com.project.chargingstationfinder.util.ConnectionsItem
import com.project.chargingstationfinder.util.show
import com.project.chargingstationfinder.viewmodel.DetailsViewModel
import com.xwray.groupie.GroupAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailsFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: DetailsViewModelFactory by instance()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this,factory)[DetailsViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
        setListeners()
    }

    private fun bindUI(){
        binding.detailsPb.show()
        /*viewModel.connectionsLiveData.observe(viewLifecycleOwner, Observer {
            binding.detailsPb.hide()
            initRecyclerView(it.toConnectionsItem())
        })*/
    }

    private fun initRecyclerView(connectionsItem: List<ConnectionsItem>) {
        val mAdapter = GroupAdapter<com.xwray.groupie.GroupieViewHolder>().apply {
            addAll(connectionsItem)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<Connections>.toConnectionsItem() : List<ConnectionsItem>{
        return this.map {
            ConnectionsItem(it)
        }
    }

    private fun setListeners() {
    }

    private fun detailsToMap() {
        findNavController().navigate(R.id.action_detailsFragment_to_mapFragment)
    }
}