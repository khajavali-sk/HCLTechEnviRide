package com.example.hcltechenviride.Models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class History {
    private val strArray: Array<String> = arrayOf("11111","21111","31111","41111","51111")
    @RequiresApi(Build.VERSION_CODES.O)
    private val now = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var date_time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    var cycleID: String = strArray.random()

    constructor()
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(date_time: String?, cycleID: String) {
        this.date_time = date_time
        this.cycleID = cycleID
    }


}