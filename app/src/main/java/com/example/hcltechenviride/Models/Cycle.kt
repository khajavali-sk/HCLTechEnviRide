package com.example.hcltechenviride.Models

class Cycle {
    var cycleID: String? = null
    var color: String? = null
    var location: String? = null
    var allotted: String? = null
    var damaged: String? = null

    constructor()
    constructor(cycleID: String?, color: String?, location: String?,allotted: String = "False",damaged: String = "False") {
        this.cycleID = cycleID
        this.color = color
        this.location = location
        this.allotted = allotted
        this.damaged = damaged
    }

}