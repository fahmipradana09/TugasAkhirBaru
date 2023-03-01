package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AdminViewModel(
    val auth: FirebaseAuth,
    private val databaseTransaction: DatabaseReference,
    private val listener: ViewModelListener
) : ObservableViewModel() {

    private val mutableListData: MutableLiveData<List<TransactionMenu>> = MutableLiveData()
    val listData: LiveData<List<TransactionMenu>> = mutableListData
    val transactionsList = mutableListOf<TransactionMenu>()

    companion object {
        const val OPEN_LIST_TRANSACTION = "open_listTransaction"
    }

    @Bindable
    var trans = TransactionMenu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    fun openHistoryTransaction() {
        listener.navigateTo(OPEN_LIST_TRANSACTION)
    }


    fun getOrder() {
        transactionsList.clear()
        databaseTransaction.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (transaction in snapshot.children) {
                    val itemList = transaction.getValue(TransactionMenu::class.java)
                    if (itemList != null) {
                        transactionsList.add(itemList)
                    }
                }

                val doneTransactions = transactionsList.filter { it.statusMakanan != "Done" }
                val dateFormat: DateFormat =
                    SimpleDateFormat(Constants.TIMESTAMP_FORMAT, Locale.getDefault())
                val sortedDate = doneTransactions.sortedByDescending { dateFormat.parse(it.date) }

                mutableListData.postValue(sortedDate)
                notifyPropertyChanged(BR.item)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}