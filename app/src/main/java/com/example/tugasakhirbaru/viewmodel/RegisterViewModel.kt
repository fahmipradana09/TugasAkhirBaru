package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class RegisterViewModel(
    val auth: FirebaseAuth,
    val database: DatabaseReference,
    val listener: Listener
) : ObservableViewModel() {
    @Bindable
    var user = Users()

    fun register() {
        if (user.isBlank()) {
            listener.showMessage("Terdapat kolom yang kosong.")
            return
        }

        database.child(user.email).setValue(user).addOnSuccessListener {
            listener.showMessage("Data telah berhasil ditambahkan.")
            listener.navigateTo(LOGIN_PAGE)

        }.addOnFailureListener {
            listener.showMessage("Gagal mendaftar.")
        }
    }

    interface Listener {
        fun showMessage(message: String, isLong: Boolean = false)
        fun navigateTo(param: String)
    }

    companion object {
        const val LOGIN_PAGE = "LOGIN_PAGE"
    }
}