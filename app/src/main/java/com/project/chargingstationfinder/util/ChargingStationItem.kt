package com.project.chargingstationfinder.util

import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.database.entities.ChargingStation
import com.project.chargingstationfinder.databinding.ItemChargingstationBinding
import com.xwray.groupie.databinding.BindableItem

class ChargingStationItem(
    private val chargingStation: ChargingStation
) : BindableItem<ItemChargingstationBinding>() {

    override fun getLayout() = R.layout.item_chargingstation

    override fun bind(viewBinding: ItemChargingstationBinding, position: Int) {
        viewBinding.chargingStation = chargingStation
    }
}