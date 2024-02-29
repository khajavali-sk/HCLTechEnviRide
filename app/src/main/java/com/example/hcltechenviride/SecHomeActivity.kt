package com.example.hcltechenviride

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hcltechenviride.databinding.ActivitySecHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SecHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the bottom navigation view
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_sec_home)
        navView.setupWithNavController(navController)
    }
}
