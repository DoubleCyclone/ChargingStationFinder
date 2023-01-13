package com.project.chargingstationfinder

import android.app.Application
import com.project.chargingstationfinder.factory.*
import com.project.chargingstationfinder.repository.ApiClient
import com.project.chargingstationfinder.repository.MapRepository
import com.project.chargingstationfinder.util.PreferenceProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApp))

        bind() from singleton { ApiClient() }
        bind() from singleton { MapRepository(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from provider { MapViewModelFactory(instance(),instance()) }
        bind() from provider { MainViewModelFactory() }
        bind() from provider { LoginViewModelFactory() }
        bind() from provider { SearchViewModelFactory(instance()) }
        bind() from provider { DetailsViewModelFactory() }
    }
}