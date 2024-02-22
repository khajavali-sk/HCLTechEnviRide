package com.example.hcltechenviride.Models

class User {
    var role: String? = null
    var name: String? = null
    var employeeId: String? = null
    var email: String? = null
    var password: String? = null

    constructor()


    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

    constructor(
        role: String?, name: String?, employeeId: String?, email: String?, password: String?
    ) {
        this.role = role
        this.name = name
        this.employeeId = employeeId
        this.email = email
        this.password = password
    }


}