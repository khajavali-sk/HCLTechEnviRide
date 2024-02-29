package com.example.hcltechenviride.Models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CurrentCycle {
    // Current date and time
    @RequiresApi(Build.VERSION_CODES.O)
    private val now = LocalDateTime.now()
    // Allotted time for the cycle
    @RequiresApi(Build.VERSION_CODES.O)
    var allottedTime: String? = null
    // Cycle ID
    var cycleID: String? = null
    // Employee ID
    var empID: String? = null

    constructor()

    // Constructor to initialize current cycle details
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(cycleID: String, empID: String) {
        this.allottedTime = now.format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"))
        this.cycleID = cycleID
        this.empID = empID
    }
}
