package com.example.hcltechenviride.Models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class History {
    @RequiresApi(Build.VERSION_CODES.O)
    private val now = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)

    var allottedTime: String? = null;
    var returnTime: String? = null;
    var empID: String? = null;
    var cycleID: String? = null;

    constructor()
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(allottedTime: String?, returnTime: String?, empID: String?, cycleID: String?) {
        this.allottedTime = allottedTime
        this.returnTime = now.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"))
        this.empID = empID
        this.cycleID = cycleID
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(cycleID: String) {
        this.allottedTime = now.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"))
        this.cycleID = cycleID
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(allottedTime: String?, cycleID: String?) {
        this.allottedTime = allottedTime
        this.cycleID = cycleID
    }


}