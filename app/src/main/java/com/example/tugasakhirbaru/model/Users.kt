package com.example.tugasakhirbaru.model

import android.os.Parcelable
import com.example.tugasakhirbaru.viewmodel.HomeViewModel
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var email: String = "",
    var role: String = "",
    var username: String = "",
    var password: String = "",
    var picture: String = "",
    var alamat: String = "",
    var phone: String = ""

):Parcelable {
    @Exclude
    fun imageExists() = picture.isNotBlank()

    @Exclude
    fun isRegistrationBlank() = email.isBlank() || username.isBlank() || password.isBlank()

    @Exclude
    fun isLoginBlank() = email.isBlank() || password.isBlank()

    @Exclude
    fun isProfileBlank() = username.isBlank() && email.isBlank() && password.isBlank()

    @Exclude
    fun isDataBlank() = alamat.isBlank() || username.isBlank() || phone.isBlank()
    @Exclude
    fun toHashMap(address: String, username: String, phone : String): HashMap<String, Any?> = hashMapOf(
        "alamat" to address,
        "username" to username,
        "phone" to phone
    )
    fun roles(role:String):HashMap<String,String> = hashMapOf(
        "role" to role
    )

}