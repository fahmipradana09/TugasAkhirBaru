package com.example.tugasakhirbaru.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class HomeViewModel(
    val database: DatabaseReference, val auth: FirebaseAuth, val listener: ViewModelListener
) : ObservableViewModel() {
    private val mutableListData: MutableLiveData<List<Menu>> = MutableLiveData()
    val listData: LiveData<List<Menu>> = mutableListData

    fun getMenuData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listData = arrayListOf<Menu>()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val menu = userSnapshot.getValue(Menu::class.java)
                        if (menu != null) {
                            listData.add(menu)
                        }
                    }
                }
                mutableListData.postValue(listData)
            }

            override fun onCancelled(error: DatabaseError) {
                mutableListData.postValue(listOf())
            }
        })

    }
}