package com.example.hcltechenviride

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.ActivityEmpLoginBinding
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

// Function to initialize EncryptedSharedPreferences
fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
    val role =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    return EncryptedSharedPreferences.create(
        context,
        "UserRoleFile",
        role,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

// Function to save data to encrypted SharedPreferences
fun setUserRole(context: Context, key: String, value: String) {
    val prefs = getEncryptedSharedPreferences(context)
    val editor = prefs.edit()
    editor.putString(key, value)
    editor.apply()
}

// Function to retrieve data from encrypted SharedPreferences


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
                                .addOnSuccessListener {uR->
//                                    val user:User = it.toObject<User>()!!
                                    setUserRole(this,"role", uR.toObject<User>()!!.role!!)

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