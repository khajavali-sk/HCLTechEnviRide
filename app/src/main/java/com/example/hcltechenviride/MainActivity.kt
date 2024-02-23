package com.example.hcltechenviride

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


// Function to retrieve data from encrypted SharedPreferences
fun getUserRole(context: Context, key: String): String? {
    val prefs = getEncryptedSharedPreferences(context)
    return prefs.getString(key, null)
}

fun checkUserRoleAndOpenActivity(context: Context) {
    val userRole = getUserRole(context,"role")

    val intent = when (userRole) {
        "Admin" -> Intent(context, AdminHomeActivity::class.java)
        "Employee" -> Intent(context, EmpHomeActivity::class.java)
        "Security" -> Intent(context, SecHomeActivity::class.java)
        else -> Intent(context, EmpLoginActivity::class.java) // Default activity for unknown role
    }

    context.startActivity(intent)
}


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = Color.TRANSPARENT

        Handler(Looper.getMainLooper()).postDelayed({
            if (FirebaseAuth.getInstance().currentUser == null)
                startActivity(Intent(this, EmpLoginActivity::class.java))
            else
            {
                checkUserRoleAndOpenActivity(this)
                finish()
            }
            finish()
        }, 3000)
    }
}