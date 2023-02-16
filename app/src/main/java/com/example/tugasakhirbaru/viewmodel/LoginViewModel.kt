package com.example.tugasakhirbaru.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import com.example.tugasakhirbaru.BuildConfig
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.repository.UserPreference
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class LoginViewModel(
    val auth: FirebaseAuth, val database: DatabaseReference,
    private val userPreference: UserPreference, val listener: ViewModelListener

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
                        if (user != null ){
                            if (user.role == "admin"){
                                listener.navigateTo(Constants.OPEN_ADMIN)
                            }else{
                                listener.navigateTo(Constants.HOME_PAGE)
                            }

                        }
                        userPreference.saveUser(user!!)
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