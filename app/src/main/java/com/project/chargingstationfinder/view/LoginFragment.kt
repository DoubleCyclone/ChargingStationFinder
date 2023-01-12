package com.project.chargingstationfinder.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.chargingstationfinder.interfaces.GeneralListener
import com.project.chargingstationfinder.databinding.FragmentLoginBinding
import com.project.chargingstationfinder.util.hide
import com.project.chargingstationfinder.util.show
import com.project.chargingstationfinder.util.toast
import com.project.chargingstationfinder.viewmodel.LoginViewModel

class LoginFragment : Fragment(), GeneralListener {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.loginViewModel = viewModel
        viewModel.generalListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewModel.checkIfLoggedIn(this)
    }

    private fun setListeners() {
        binding.signInbtn.setOnClickListener {
            viewModel.logIn(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStarted(message: String) {
        activity?.toast(message)
        binding.loginPb.show()
    }

    override fun onSuccess(message: String, generalResponse: LiveData<String>?) {
        generalResponse?.observe(this, Observer {
            binding.loginPb.hide()
            activity?.toast(it)
        }) ?: run {
            binding.loginPb.hide()
            activity?.toast(message)
        }
    }

    override fun onFailure(message: String) {
        binding.loginPb.hide()
        activity?.toast(message)
    }
}