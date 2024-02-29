package com.example.hcltechenviride.fragments_admin

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

        // Set up spinner for cycle color
        val cycleColor = binding.cycleColor
        val colors = listOf("Red", "Black", "Blue", "Green")
        val colorAdapter = ArrayAdapter(this@AddCyclesActivity, android.R.layout.simple_spinner_item, colors)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cycleColor.adapter = colorAdapter

        // Set up spinner for cycle location
        val location = binding.location
        val locations = listOf(
            "Hyderabad", "Madurai", "Nagpur", "Vijayawada",
            "Lucknow", "Noida", "Chennai", "Bangalore"
        )
        val locationAdapter = ArrayAdapter(this@AddCyclesActivity, android.R.layout.simple_spinner_item, locations)
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        location.adapter = locationAdapter

        // Add button click listener
        binding.addBtn.setOnClickListener {
            val selectedColor = cycleColor.selectedItem.toString()
            val selectedLocation = location.selectedItem.toString()

            // Check if cycle ID is empty
            if (binding.cycleID.editText?.text.toString().isEmpty()) {
                Toast.makeText(this@AddCyclesActivity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                // Get cycle ID
                val cycleId = binding.cycleID.editText?.text.toString()

                // Create Cycle object
                val cycle = Cycle(cycleId, selectedColor, selectedLocation)

                // Add cycle to Firestore
                Firebase.firestore.collection(CYCLE_FOLDER).document(cycle.cycleID!!).set(cycle)
                    .addOnSuccessListener {
                        Toast.makeText(this@AddCyclesActivity, "Cycle added successfully", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@AddCyclesActivity, "Failed adding cycle $cycleId", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
