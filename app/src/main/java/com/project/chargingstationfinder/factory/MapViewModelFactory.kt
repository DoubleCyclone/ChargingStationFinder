package com.project.chargingstationfinder.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.chargingstationfinder.database.AppDatabase
import com.project.chargingstationfinder.repository.MapRepository
import com.project.chargingstationfinder.util.PreferenceProvider
import com.project.chargingstationfinder.viewmodel.MapViewModel

@Suppress("UNCHECKED_CAST")
class MapViewModelFactory(
    private val repository: MapRepository,
    private val prefs : PreferenceProvider
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(repository,prefs) as T
    }
}