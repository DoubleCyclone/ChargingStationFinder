package com.project.chargingstationfinder.interfaces

import androidx.lifecycle.LiveData

interface GeneralListener {
    fun onStarted(message:String)
    fun onSuccess(message:String , generalResponse: LiveData<String>?)
    fun onFailure(message:String)
}