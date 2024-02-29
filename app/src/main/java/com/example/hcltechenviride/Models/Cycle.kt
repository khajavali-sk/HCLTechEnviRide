package com.example.hcltechenviride.Models

class Cycle {
    // Cycle ID
    var cycleID: String? = null

    // Color of the cycle
    var color: String? = null

    // Location of the cycle
    var location: String? = null

    // Whether the cycle is allotted or not
    var allotted: String? = null

    // Whether the cycle is damaged or not
    var damaged: String? = null

    constructor()

    // Constructor to initialize cycle details
    constructor(
        cycleID: String?,
        color: String?,
        location: String?,
        allotted: String = "False",
        damaged: String = "False"
    ) {
        this.cycleID = cycleID
        this.color = color
        this.location = location
        this.allotted = allotted
        this.damaged = damaged
    }
}
