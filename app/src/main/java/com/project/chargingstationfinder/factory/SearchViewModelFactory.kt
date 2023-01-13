package com.project.chargingstationfinder.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.chargingstationfinder.util.PreferenceProvider
import com.project.chargingstationfinder.viewmodel.LoginViewModel
import com.project.chargingstationfinder.viewmodel.SearchViewModel

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val prefs : PreferenceProvider
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(prefs) as T
    }
}