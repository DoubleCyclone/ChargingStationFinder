package com.project.chargingstationfinder.view


import SharedPreferencesHelper
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.project.chargingstationfinder.R
import com.project.chargingstationfinder.databinding.ActivityMainBinding
import com.project.chargingstationfinder.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        mainViewModel.navController = navHostFragment.navController

        supportActionBar?.title = getString(R.string.student_id)

        SharedPreferencesHelper.init(this)
        mainViewModel.permissions(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutMenubtn -> {
                mainViewModel.logOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}


