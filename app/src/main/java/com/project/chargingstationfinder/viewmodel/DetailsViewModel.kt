package com.project.chargingstationfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.chargingstationfinder.database.entities.Connections

class DetailsViewModel : ViewModel(){

    private var _connectionsLiveData = MutableLiveData<List<Connections>>()
    val connectionsLiveData: LiveData<List<Connections>>
        get() = _connectionsLiveData

    init {
        //connectionsLiveData.value?.toMutableList()
    }
}