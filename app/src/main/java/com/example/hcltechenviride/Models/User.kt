package com.example.hcltechenviride.Models

class User {
    // User role
    var role: String? = null
    // User name
    var name: String? = null
    // User employee ID
    var employeeId: String? = null
    // User email
    var email: String? = null
    // User password
    var password: String? = null

    constructor()

    // Constructor used for login
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

    // Constructor used for registration
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
