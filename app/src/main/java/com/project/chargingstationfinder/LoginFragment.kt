package com.project.chargingstationfinder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.huawei.agconnect.api.AGConnectApi
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.AGConnectAuthCredential
import com.project.chargingstationfinder.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        if(AGConnectAuth.getInstance().currentUser != null){
            loginToSearch()
        }
    }

    private fun setListeners() {
        binding.signInbtn.setOnClickListener {
            AGConnectAuth.getInstance()
                .signIn(activity, AGConnectAuthCredential.HMS_Provider)
                .addOnSuccessListener {
                    Toast.makeText(activity, "successful", Toast.LENGTH_SHORT).show()
                    loginToSearch()
                }.addOnFailureListener {
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun loginToSearch() {
        findNavController().navigate(R.id.action_loginFragment_to_searchFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        AGConnectApi.getInstance().activityLifecycle()
            .onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}