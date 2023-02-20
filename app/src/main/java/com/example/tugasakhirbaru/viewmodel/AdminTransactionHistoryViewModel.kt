package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class AdminTransactionHistoryViewModel(
    private val databaseTransaction: DatabaseReference,
    val auth: FirebaseAuth
) : ObservableViewModel() {
    private val mutableListData: MutableLiveData<List<TransactionMenu>> = MutableLiveData()
    val listData: LiveData<List<TransactionMenu>> = mutableListData

    @Bindable
    var item = TransactionMenu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    fun getMenuData() {
        databaseTransaction.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listData = arrayListOf<TransactionMenu>()
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val transactionData = dataSnapshot.getValue(TransactionMenu::class.java)
                        if (transactionData != null){
                                listData.add(transactionData)
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