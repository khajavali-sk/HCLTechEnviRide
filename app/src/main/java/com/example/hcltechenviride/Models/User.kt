package com.example.hcltechenviride.Models

class User {
    var name: String? = null
    var employeeId: String? = null
    var email: String? = null
    var password: String? = null
    constructor()

    constructor(name: String?, employeeId: String?, email: String?, password: String?) {
        this.name = name
        this.employeeId = employeeId
        this.email = email
        this.password = password
    }

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }


}