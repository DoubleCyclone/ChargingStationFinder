package com.project.chargingstationfinder.viewmodel

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.huawei.agconnect.api.AGConnectApi
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.AGConnectAuthCredential
import com.project.chargingstationfinder.R

class LoginViewModel: ViewModel() {

    fun logIn(activity:Activity,fragment: Fragment){
        AGConnectAuth.getInstance()
            .signIn(activity, AGConnectAuthCredential.HMS_Provider)
            .addOnSuccessListener {
                Toast.makeText(activity, "successful", Toast.LENGTH_SHORT).show()
                loginToSearch(fragment)
            }.addOnFailureListener {
                Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    fun checkIfLoggedIn(fragment: Fragment){
        if (AGConnectAuth.getInstance().currentUser != null) {
            loginToSearch(fragment)
        }
    }

    private fun loginToSearch(fragment:Fragment) {
        fragment.findNavController().navigate(R.id.action_loginFragment_to_searchFragment)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        AGConnectApi.getInstance().activityLifecycle()
            .onActivityResult(requestCode, resultCode, data)
    }
}