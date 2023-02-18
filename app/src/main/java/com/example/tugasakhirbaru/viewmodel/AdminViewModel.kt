package com.example.tugasakhirbaru.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class AdminViewModel(
    val auth: FirebaseAuth,
    private val databaseTransaction: DatabaseReference,
    private val listener: ViewModelListener
) : ObservableViewModel() {

    private val mutableListData: MutableLiveData<List<TransactionMenu>> = MutableLiveData()
    val listData: LiveData<List<TransactionMenu>> = mutableListData
    val transactionsList = mutableListOf<TransactionMenu>()

    @Bindable
    var trans = TransactionMenu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }


    fun getOrder() {
        transactionsList.clear()
        databaseTransaction.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (transaction in snapshot.children) {
                    val itemList = transaction.getValue(TransactionMenu::class.java)
                    if (itemList != null) {
                        transactionsList.add(itemList)
                        Log.i("tes", "$itemList, $transactionsList")

                    }
                }
                val doneTransactions = transactionsList.filter { it.status != "Done" }

                mutableListData.postValue(doneTransactions)
                notifyPropertyChanged(BR.item)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}