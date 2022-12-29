package com.project.chargingstationfinder

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.huawei.agconnect.auth.AGConnectAuth
import com.project.chargingstationfinder.databinding.ActivityMainBinding


class MainActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        supportActionBar?.title = getString(R.string.student_id)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutMenubtn -> {
                AGConnectAuth.getInstance().signOut()
                navController.navigate(R.id.loginFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}