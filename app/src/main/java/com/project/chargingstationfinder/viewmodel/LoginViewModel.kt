package com.project.chargingstationfinder.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.huawei.agconnect.api.AGConnectApi
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.AGConnectAuthCredential
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.interfaces.LoginListener
import com.project.chargingstationfinder.view.LoginFragment

class LoginViewModel : ViewModel() {

    var loginListener: LoginListener? = null

    fun logIn(view: LoginFragment) {
        AGConnectAuth.getInstance()
            .signIn(view.activity, AGConnectAuthCredential.HMS_Provider)
            .addOnSuccessListener {
                Toast.makeText(view.activity, "successful", Toast.LENGTH_SHORT).show()
                loginToSearch(view)
            }.addOnFailureListener {
                Toast.makeText(view.activity, it.message.toString(), Toast.LENGTH_SHORT).show()
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