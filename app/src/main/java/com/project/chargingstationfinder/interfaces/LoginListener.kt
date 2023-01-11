package com.project.chargingstationfinder.interfaces

interface LoginListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message:String)
}