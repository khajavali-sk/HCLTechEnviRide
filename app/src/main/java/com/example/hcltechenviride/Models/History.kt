package com.example.hcltechenviride.Models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class History {
    @RequiresApi(Build.VERSION_CODES.O)
    private val now = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)

    var empID: String? = null;
    var cycleID: String? = null;
    var duration: String? = null;

    constructor()
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(cycleID: String?, empID: String?,allottedTime: String? ) {
        this.duration = "$allottedTime  to  "+now.format(DateTimeFormatter.ofPattern("dd-MM-yy" +
                " HH:mm"))
        this.empID = empID
        this.cycleID = cycleID
    }


}