package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

//class ProfileEditViewModel(
//    val auth: FirebaseAuth, val database: DatabaseReference, val listener: ViewModelListener
//) : ObservableViewModel(){
//    @Bindable
//    var user = Users()
//
//    @Bindable
//    var isLoading = false
//
//    val userId = auth.currentUser?.uid
//
//    fun edit(){
//        if(user.isProfileBlank()){
//            listener.showMessage("Akun masih belum melakuka LOGIN")
//            return
//        }
//
//        isLoading = true
//
//        userUpdate(user.email, user.username, user.picture, user.alamat, user.phone)
//
//    }
//
//    fun userUpdate(email: String, username: String, picture: String, alamat: String, phone: String){
//        val user = mapOf<String, String>(
//            "email" to email,
//            "username" to username,
//            "picture" to picture,
//            "alamat" to alamat,
//            "phone" to phone
//        )
//
//
//        if (!userId.isNullOrBlank()) {
//            database.child(userId).updateChildren(user).addOnSuccessListener {
//                listener.showMessage("Profile anda telah diperbarui")
//
//            }.addOnFailureListener{
//
//                listener.showMessage("Terjadi kendala dalam memperbarui!")
//
//            }
//        }
//    }
//
//}
