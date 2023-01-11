package com.project.chargingstationfinder.view

import SharedPreferencesHelper
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.ActivityMainBinding
import com.project.chargingstationfinder.misc.toast
import com.project.chargingstationfinder.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.mainViewModel = viewModel

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        viewModel.navController = navHostFragment.navController

        supportActionBar?.title = getString(R.string.student_id)

        SharedPreferencesHelper.init(this)
        viewModel.permissions(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutMenubtn -> {
                viewModel.logOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


