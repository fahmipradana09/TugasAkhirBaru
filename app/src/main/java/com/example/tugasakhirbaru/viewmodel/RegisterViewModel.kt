package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants.LOGIN_PAGE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class RegisterViewModel(
    val auth: FirebaseAuth, val database: DatabaseReference, val listener: ViewModelListener
) : ObservableViewModel() {
    @Bindable
    var user = Users()

    @Bindable
    var isLoading = false

    fun register() {
        if (user.isRegistrationBlank()) {
            listener.showMessage("Terdapat kolom yang kosong.")
            return
        }

        isLoading = true

        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { result ->
            isLoading = false
            val userId = auth.currentUser?.uid
            if (result.isSuccessful && !userId.isNullOrBlank()) {
                database.child(userId).setValue(user).addOnSuccessListener {
                    database.child(userId).updateChildren(user.roles("users") as Map<String, Any>)
                    listener.showMessage("Data telah berhasil ditambahkan.")
                    listener.navigateTo(LOGIN_PAGE)
                }.addOnFailureListener {
                    listener.showMessage("Gagal mendaftar.")
                }
            } else {
                if (userId.isNullOrBlank()) {
                    listener.showMessage("Terjadi masalah saat pendaftaran user.")
                }

                // NOTE: Take note error message dalam bahasa Inggris
                listener.showMessage(result.exception?.message)
            }
        }
    }
}