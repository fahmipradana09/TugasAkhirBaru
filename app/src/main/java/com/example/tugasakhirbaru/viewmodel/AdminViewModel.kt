package com.example.tugasakhirbaru.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Cart
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
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



   /* fun getTransaction () : LiveData<List<TransactionMenu>>{
        databaseTransaction.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val transactionMutable = mutableListOf<TransactionMenu>()
                for (transactionSnapshot in snapshot.children){
                    val trans = transactionSnapshot.getValue(TransactionMenu::class.java)
                    if (trans != null) {
                        transactionMutable.add(trans)
                    }
                }
                transactionMutable.sortBy { it.date }
                transactionsList.value = transactionMutable
                notifyPropertyChanged(BR.item)
            }

            override fun onCancelled(error: DatabaseError) {
                listener.showMessage(error.message)
            }
        })
        return transactionsList
    }

    fun getOldesetTransaction(): TransactionMenu?{
        val transactionList = transactionsList.value
        if(transactionList != null){
            for (transaction in transactionList){
                if (transaction.status != "done"){
                    return transaction
                }
            }
        }
        return null
    }*/

    fun getOrder(){
        databaseTransaction.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (transaction in snapshot.children){
                    val itemList = transaction.getValue(TransactionMenu::class.java)
                    if (itemList != null){
                       transactionsList.add(itemList)
                        Log.i("tes", "$itemList, $transactionsList")

                    }
                }
                val doneTransactions = transactionsList.filter { it.status == "done" }
                doneTransactions.sortedBy { it.date }

                for (transactions in transactionsList){
                    if(transactions.status != "done"){
                        trans = transactions
                    break
                    }
                }
                mutableListData.postValue(transactionsList)
                notifyPropertyChanged(BR.item)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}