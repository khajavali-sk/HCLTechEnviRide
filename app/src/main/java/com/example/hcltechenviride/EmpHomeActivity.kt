package com.example.hcltechenviride

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hcltechenviride.databinding.ActivityEmpHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class EmpHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmpHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmpHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_emp_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}