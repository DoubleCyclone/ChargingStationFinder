package com.project.chargingstationfinder.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.chargingstationfinder.repository.MapRepository
import com.project.chargingstationfinder.viewmodel.MapViewModel

@Suppress("UNCHECKED_CAST")
class MapViewModelFactory(
    private val repository: MapRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(repository) as T
    }
}