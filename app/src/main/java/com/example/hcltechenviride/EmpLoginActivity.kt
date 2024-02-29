package com.example.hcltechenviride

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.ActivityEmpLoginBinding
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.example.hcltechenviride.utils.EncryptedSharedPrefs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class EmpLoginActivity : AppCompatActivity() {

    // Using view binding to access UI elements
    private val binding by lazy {
        ActivityEmpLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Customize the "Don't have an account?" text
        val text =
            "<font color=#FF000000>Don't have an account?</font> <font color=#1E88E5>Register</font>"
        binding.toRegister.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)

        binding.loginBtn.setOnClickListener {
            if (binding.email.editText?.text.toString().isEmpty() ||
                binding.password.editText?.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    this@EmpLoginActivity, "Please fill all the fields", Toast.LENGTH_SHORT
                ).show()
            } else {
                val user = User(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                )

                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener { it ->
                        if (it.isSuccessful) {
                            // Fetch user details from Firestore
                            Firebase.firestore.collection(EMP_USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid)
                                .get().addOnSuccessListener { document ->
                                    val user: User = document.toObject<User>()!!
                                    // Store user role and employee ID in shared preferences
                                    EncryptedSharedPrefs.setUserRole(this, user.role!!)
                                    EncryptedSharedPrefs.setCurrentEmployeeId(this, user.employeeId!!)
                                    Toast.makeText(this@EmpLoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                                }
                            // Navigate to main activity on successful login
                            startActivity(
                                Intent(
                                    this@EmpLoginActivity, MainActivity::class.java
                                )
                            )
                            finish()
                        } else {
                            // Show error message if login fails
                            Toast.makeText(
                                this@EmpLoginActivity,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        binding.toRegister.setOnClickListener {
            // Navigate to registration activity when "Register" is clicked
            startActivity(Intent(this@EmpLoginActivity, SignUpActivity::class.java))
            finish()
        }
    }
}
