package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.google.firebase.database.DatabaseReference

class AdminConfirmViewModel(
    private val transactionDatabase: DatabaseReference,
    private val listener: ViewModelListener,
) : ObservableViewModel() {

    private val mutableDataMenu: MutableLiveData<Menu> = MutableLiveData()
    val dataMenu: LiveData<Menu> = mutableDataMenu

    @Bindable
    var item = TransactionMenu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }


    fun updateStatusPembayaran(statusPembayaran: String) {
        // Update value dari status salah satu transaksi
        if (statusPembayaran.isBlank()) {
            return
        }
        transactionDatabase.child(item.id)
            .updateChildren(mapOf("statusPembayaran" to statusPembayaran))
            .addOnCompleteListener {
               listener.showMessage("Status Telah Diganti")
            }
    }



}