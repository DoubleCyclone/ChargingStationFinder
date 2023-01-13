package com.project.chargingstationfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.responses.ChargingStation
import com.project.chargingstationfinder.responses.Connections

class DetailsViewModel : ViewModel(){

    private var _connectionsLiveData = MutableLiveData<List<Connections>>()
    val connectionsLiveData: LiveData<List<Connections>>
        get() = _connectionsLiveData

    init {
        //connectionsLiveData.value?.toMutableList()
    }
}