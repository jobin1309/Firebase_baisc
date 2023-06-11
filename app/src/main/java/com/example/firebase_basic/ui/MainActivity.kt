package com.example.firebase_basic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.firebase_basic.R
import com.example.firebase_basic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        var navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHost

        navController = navHostFragment.navController

        setupActionBarWithNavController(navController);

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
