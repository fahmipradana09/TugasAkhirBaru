package com.example.tugasakhirbaru.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener

class AdminViewModel(
    val auth: FirebaseAuth,
    private val databaseTransaction: DatabaseReference,
    private val listener: ViewModelListener
) : ObservableViewModel() {

    @Bindable
    var trans = Transaction()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }


    fun getOrder(){
        val id = databaseTransaction.push().key ?: ""
        databaseTransaction.child(id).orderByChild("date").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children){
                    Log.i("tes", "$child")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}