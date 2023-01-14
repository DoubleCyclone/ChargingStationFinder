package com.project.chargingstationfinder.util

import android.view.View
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.database.entities.ChargingStation
import com.project.chargingstationfinder.databinding.ItemConnectionsBinding
import com.project.chargingstationfinder.database.entities.Connections
import com.project.chargingstationfinder.databinding.ItemChargingstationBinding
import com.xwray.groupie.viewbinding.BindableItem

class ChargingStationItem(
    private val chargingStation: ChargingStation
) : BindableItem<ItemChargingstationBinding>() {

    override fun getLayout() = R.layout.item_chargingstation

    override fun initializeViewBinding(view: View): ItemChargingstationBinding {
        TODO("Not yet implemented")
    }

    override fun bind(viewBinding: ItemChargingstationBinding, position: Int) {
        viewBinding.chargingStation = chargingStation
    }
}