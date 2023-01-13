package com.project.chargingstationfinder.util

import android.view.View
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.ItemConnectionsBinding
import com.project.chargingstationfinder.json.Connections
import com.xwray.groupie.viewbinding.BindableItem

class ConnectionsItem(
    private val connections : Connections
) : BindableItem<ItemConnectionsBinding>() {

    override fun bind(viewBinding: ItemConnectionsBinding, position: Int) {
        viewBinding.connection = connections
    }

    override fun getLayout() = R.layout.item_connections

    override fun initializeViewBinding(view: View): ItemConnectionsBinding {
        TODO("Not yet implemented")
    }
}