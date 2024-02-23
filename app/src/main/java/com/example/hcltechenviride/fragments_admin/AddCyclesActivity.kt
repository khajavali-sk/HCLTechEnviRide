package com.example.hcltechenviride.fragments_admin

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hcltechenviride.Models.Cycle
import com.example.hcltechenviride.databinding.ActivityAddCyclesBinding
import com.example.hcltechenviride.utils.CYCLE_FOLDER
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddCyclesActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAddCyclesBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var cycle = Cycle()
        var cycleID = binding.cycleID

        val cycleColor = binding.cycleColor
        val colors = listOf("Red", "Black", "Blue", "Green")
        val colorAdapter = ArrayAdapter(this@AddCyclesActivity, R.layout.simple_spinner_item, colors)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cycleColor.adapter = colorAdapter

        val location = binding.location
        val roles = listOf(
            "Hyderabad",
            "Madurai",
            "Nagpur",
            "Vijayawada",
            "Lucknow",
            "Noida",
            "Chennai",
            "Bangalore"
        )
        val locationAdapter =
            ArrayAdapter(this@AddCyclesActivity, R.layout.simple_spinner_item, roles)
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        location.adapter = locationAdapter

        binding.addBtn.setOnClickListener {
            val selectedColor = cycleColor.selectedItem.toString()
            val selectedLocation = location.selectedItem.toString()

            if (binding.cycleID.editText?.text.toString().isEmpty()) {
                Toast.makeText(this@AddCyclesActivity, "please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                cycle.cycleID = binding.cycleID.editText?.text.toString()
                cycle.color = selectedColor
                cycle.location = selectedLocation
                Firebase.firestore.collection(CYCLE_FOLDER).document(cycle.cycleID!!).set(cycle)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@AddCyclesActivity,
                            "Cycle added Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    }.addOnFailureListener {
                        Toast.makeText(
                            this@AddCyclesActivity,
                            "Failed Adding Cycle $cycleID",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }

        }

    }
}