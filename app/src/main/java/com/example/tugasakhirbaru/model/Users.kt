package com.example.tugasakhirbaru.model

data class Users(
    var email: String = "",
    var username: String = "",
    var password: String = ""
) {
    fun isBlank() = email.isBlank() || username.isBlank() || password.isBlank()
}