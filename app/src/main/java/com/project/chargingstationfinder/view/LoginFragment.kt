package com.project.chargingstationfinder.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.chargingstationfinder.interfaces.LoginListener
import com.project.chargingstationfinder.databinding.FragmentLoginBinding
import com.project.chargingstationfinder.misc.toast
import com.project.chargingstationfinder.viewmodel.LoginViewModel

class LoginFragment : Fragment(), LoginListener{

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        loginViewModel.checkIfLoggedIn(this)
    }

    private fun setListeners() {
        binding.signInbtn.setOnClickListener {
            loginViewModel.logIn(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginViewModel.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStarted() {
        activity?.toast("Login Started")
    }

    override fun onSuccess() {
        activity?.toast("Login Success")
    }

    override fun onFailure(message: String) {
        activity?.toast(message)
    }
}