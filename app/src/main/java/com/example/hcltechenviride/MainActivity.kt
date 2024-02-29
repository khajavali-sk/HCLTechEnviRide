package com.example.hcltechenviride

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.hcltechenviride.utils.EncryptedSharedPrefs
import com.google.firebase.auth.FirebaseAuth

// Function to retrieve data from encrypted SharedPreferences
fun checkUserRoleAndOpenActivity(context: Context) {
    // Retrieve the user's role from SharedPreferences
    val userRole = EncryptedSharedPrefs.getUserRole(context)

    // Determine the appropriate activity based on the user's role
    val intent = when (userRole) {
        "Admin" -> Intent(context, AdminHomeActivity::class.java)
        "Employee" -> Intent(context, EmpHomeActivity::class.java)
        "Security" -> Intent(context, SecHomeActivity::class.java)
        else -> Intent(context, EmpLoginActivity::class.java) // Default activity for unknown role
    }

    // Start the selected activity
    context.startActivity(intent)
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the status bar color to transparent
        window.statusBarColor = Color.TRANSPARENT

        // Delay execution to show a splash screen effect
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if the user is authenticated (logged in)
            if (FirebaseAuth.getInstance().currentUser == null) {
                // If not authenticated, start the login activity
                startActivity(Intent(this, EmpLoginActivity::class.java))
            } else {
                // If authenticated, determine the user's role and open the appropriate activity
                checkUserRoleAndOpenActivity(this)
                finish()
            }
            finish()
        }, 3000) // Show splash screen for 3 seconds
    }
}
