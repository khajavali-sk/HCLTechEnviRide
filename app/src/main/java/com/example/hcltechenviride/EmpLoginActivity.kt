package com.example.hcltechenviride

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.ActivityEmpLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class EmpLoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityEmpLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val text =
//            "<font color=#FF000000>Don't have an account?</font> <font color=#1E88E5>Register</font>"
//        binding.toRegister.setText(Html.fromHtml(text))

        binding.loginBtn.setOnClickListener {
            if (binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(
                    this@EmpLoginActivity,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val user = User(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                )

                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            startActivity(
                                Intent(
                                    this@EmpLoginActivity,
                                    EmpHomeActivity::class.java
                                )
                            )
                            finish()
                        } else {
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
            startActivity(Intent(this@EmpLoginActivity, SignUpActivity::class.java))
            finish()
        }

    }
}