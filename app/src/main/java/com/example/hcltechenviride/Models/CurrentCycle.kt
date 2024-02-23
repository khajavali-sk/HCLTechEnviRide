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
    lateinit var cycleID: String;

    constructor()
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(cycleID: String) {
        this.allottedTime = now.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"))
        this.cycleID = cycleID
    }
}