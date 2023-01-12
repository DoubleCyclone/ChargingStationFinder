package com.project.chargingstationfinder.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.huawei.agconnect.api.AGConnectApi
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.AGConnectAuthCredential
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.view.LoginFragment

class LoginViewModel : ViewModel() {

    var generalListener: GeneralListener? = null

    fun logIn(view: LoginFragment) {
        generalListener?.onStarted("Login Started")
        AGConnectAuth.getInstance()
            .signIn(view.activity, AGConnectAuthCredential.HMS_Provider)
            .addOnSuccessListener {
                loginToSearch(view)
                generalListener?.onSuccess("Login Successful",null)
            }.addOnFailureListener {
                generalListener?.onFailure(it.message.toString())
            }
    }

    fun checkIfLoggedIn(view: LoginFragment) {
        if (AGConnectAuth.getInstance().currentUser != null) {
            loginToSearch(view)
        }
    }

    private fun loginToSearch(view: LoginFragment) {
        view.findNavController().navigate(R.id.action_loginFragment_to_searchFragment)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        AGConnectApi.getInstance().activityLifecycle()
            .onActivityResult(requestCode, resultCode, data)
    }
}