package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class ConfirmViewModel(
    val auth: FirebaseAuth,
    val transactionDatabase: DatabaseReference,
    private val listener: ViewModelListener
) : ObservableViewModel() {

    private val mutableListData: MutableLiveData<List<TransactionMenu>> = MutableLiveData()
    val listData: LiveData<List<TransactionMenu>> = mutableListData
    val transactionsList = mutableListOf<TransactionMenu>()


    @Bindable
    var item = TransactionMenu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }


//    fun updateStatusPembayaran(statusPembayaran: String) {
//        // Update value dari status salah satu transaksi
//        if (statusPembayaran.isBlank()) {
//            return
//        }
//        transactionDatabase.child(item.id)
//            .updateChildren(mapOf("statusPembayaran" to statusPembayaran))
//            .addOnCompleteListener {
//                listener.showMessage("Status Telah Diganti")
//            }
//    }
}