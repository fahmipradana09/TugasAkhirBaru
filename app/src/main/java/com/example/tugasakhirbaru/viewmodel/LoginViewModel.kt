package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class LoginViewModel(
    val auth: FirebaseAuth, val database: DatabaseReference, val listener: ViewModelListener
) : ObservableViewModel() {
    @Bindable
    var user = Users()

    @Bindable
    var isLoading = false

    fun login() {
        if (user.isLoginBlank()) {
            listener.showMessage("Terdapat kolom yang kosong.")
            return
        }

        isLoading = true

        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            val userId = auth.currentUser?.uid

            isLoading = false

            if (it.isSuccessful && !userId.isNullOrBlank()) {
                 database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val user = snapshot.getValue(Users::class.java)
                            // NOTE: Simpan user ke SharedPreference nanti.
                            listener.showMessage("Login $userId telah berhasil dilakukan.")
                            listener.navigateTo(Constants.HOME_PAGE)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            listener.showMessage(error.message)
                        }
                    })
            } else {
                if (userId.isNullOrBlank()) {
                    listener.showMessage("Terjadi masalah saat autentikasi.")
                }

                listener.showMessage(it.exception?.message)
            }
        }
    }
}