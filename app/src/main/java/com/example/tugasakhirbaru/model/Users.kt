package com.example.tugasakhirbaru.model

import com.google.firebase.database.Exclude

data class Users(
    var email: String = "",
    var role: String = "users",
    var username: String = "",
    var password: String = ""
) {
    @Exclude
    fun isRegistrationBlank() = email.isBlank() || username.isBlank() || password.isBlank()

    @Exclude
    fun isLoginBlank() = email.isBlank() || password.isBlank()
}