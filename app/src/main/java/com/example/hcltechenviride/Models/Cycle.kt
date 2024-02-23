package com.example.hcltechenviride.Models

class Cycle {
    var cycleID: String? = null
    var color: String? = null
    var location: String? = null

    constructor()
    constructor(cycleID: String?, color: String?, location: String?) {
        this.cycleID = cycleID
        this.color = color
        this.location = location
    }

}