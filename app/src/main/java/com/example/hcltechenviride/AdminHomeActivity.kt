package com.example.hcltechenviride

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hcltechenviride.databinding.ActivityAdminHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflating the layout using view binding
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // Finding the navigation controller associated with the NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment_activity_admin_home)

        // Setting up the bottom navigation view with the navigation controller
        navView.setupWithNavController(navController)
    }
}
