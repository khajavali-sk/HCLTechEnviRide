package com.example.hcltechenviride

import android.content.Intent
import android.os.Bundle
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
            if (binding.email.editText?.text.toString()
                    .equals("") or binding.password.editText?.text.toString().equals("")
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

                            Firebase.firestore.collection(EMP_USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                                .addOnSuccessListener {
                                    val user:User = it.toObject<User>()!!
                                    EncryptedSharedPrefs.setUserRole(this, user.role!!)
                                    EncryptedSharedPrefs.setCurrentEmployeeId(this,user.employeeId!!)

                                    Toast.makeText(this@EmpLoginActivity,"Login Success",Toast.LENGTH_SHORT).show()
                                }
                            startActivity(
                                Intent(
                                    this@EmpLoginActivity, MainActivity::class.java
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