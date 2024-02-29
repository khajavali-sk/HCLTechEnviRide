package com.example.hcltechenviride.Models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class History {
    // Current LocalDateTime
    @RequiresApi(Build.VERSION_CODES.O)
    private val now = LocalDateTime.now()

    // Employee ID
    var empID: String? = null
    // Cycle ID
    var cycleID: String? = null
    // Duration of cycle usage
    var duration: String? = null

    constructor()

    // Constructor to initialize history details
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(cycleID: String?, empID: String?, allottedTime: String?) {
        this.duration = "$allottedTime to ${now.format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"))}"
        this.empID = empID
        this.cycleID = cycleID
    }
}
