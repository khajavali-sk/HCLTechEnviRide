package com.example.hcltechenviride.Models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class History {
    @RequiresApi(Build.VERSION_CODES.O)
    private val now = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)

    var allotedTime: String? = null;
    var returnTime: String? = null;
    var empID: String? = null;
    var cycleID: String? = null;

    constructor()
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(allotedTime: String?, returnTime: String?, empID: String?, cycleID: String?) {
        this.allotedTime = allotedTime
        this.returnTime = now.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"))
        this.empID = empID
        this.cycleID = cycleID
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(cycleID: String) {
        this.allotedTime = now.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"))
        this.cycleID = cycleID
    }


}