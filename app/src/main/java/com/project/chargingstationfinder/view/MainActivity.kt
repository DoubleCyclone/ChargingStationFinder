package com.project.chargingstationfinder.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.ActivityMainBinding
import com.project.chargingstationfinder.factory.MainViewModelFactory
import com.project.chargingstationfinder.viewmodel.MainViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        viewModel.navController = navHostFragment.navController

        supportActionBar?.title = getString(R.string.student_id)

        //SharedPreferencesHelper.init(this)
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


