package com.example.hcltechenviride.Models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CurrentCycle {

    @RequiresApi(Build.VERSION_CODES.O)
    private val now = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var allottedTime: String? = null
    var cycleID: String? = null
    var empID: String? = null

    constructor()
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(cycleID: String,empID: String) {
        this.allottedTime = now.format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"))
        this.cycleID = cycleID
        this.empID = empID
    }
}