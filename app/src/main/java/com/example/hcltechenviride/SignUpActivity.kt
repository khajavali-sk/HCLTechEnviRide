package com.example.hcltechenviride

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.ActivitySignUpBinding
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    // Using view binding to access UI elements
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Customize the "Already have an account?" text
        val text = "<font color=#FF000000>Already have an account?</font> <font color=#1E88E5>Login</font>"
        binding.toLogin.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)

        user = User()

        // Initialize the Spinner with user roles
        val spinnerUserRole = binding.userRole
        val roles = listOf("Employee", "Security")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUserRole.adapter = adapter

        binding.registerBtn.setOnClickListener {
            // Retrieve selected role from Spinner
            val selectedRole = spinnerUserRole.selectedItem.toString()

            // Check if all fields are filled
            if (binding.name.editText?.text.toString().isEmpty() ||
                binding.id.editText?.text.toString().isEmpty() ||
                binding.email.editText?.text.toString().isEmpty() ||
                binding.password.editText?.text.toString().isEmpty()) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Create user in Firebase Authentication
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        // Set user details
                        user.name = binding.name.editText?.text.toString()
                        user.employeeId = binding.id.editText?.text.toString()
                        user.email = binding.email.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()
                        user.role = selectedRole  // Store selected role

                        // Insert user details into Firestore
                        Firebase.firestore.collection(EMP_USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                // Navigate to login activity on successful registration
                                startActivity(
                                    Intent(
                                        this@SignUpActivity,
                                        EmpLoginActivity::class.java
                                    )
                                )
                                finish()
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "Registration Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        // Show error message if registration fails
                        Toast.makeText(
                            this@SignUpActivity,
                            result.exception?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.toLogin.setOnClickListener {
            // Navigate to login activity when "Login" is clicked
            startActivity(Intent(this@SignUpActivity, EmpLoginActivity::class.java))
            finish()
        }
    }
}
