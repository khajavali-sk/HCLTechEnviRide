package com.example.hcltechenviride

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.Toast
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.ActivitySignUpBinding
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text = "<font color=#FF000000>Already have an account?</font> <font color=#1E88E5>Login</font>"
        binding.toLogin.setText(Html.fromHtml(text))
        user = User()

        binding.registerBtn.setOnClickListener {
            if (binding.name.editText?.text.toString().equals("") or
                binding.id.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.name = binding.name.editText?.text.toString()
                        user.employeeId = binding.id.editText?.text.toString()
                        user.email = binding.email.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()
                        Firebase.firestore.collection(EMP_USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {

                                startActivity(
                                    Intent(
                                        this@SignUpActivity,
                                        EmpHomeActivity::class.java
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
            startActivity(Intent(this@SignUpActivity, EmpLoginActivity::class.java))
            finish()
        }
    }
}