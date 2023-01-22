package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.google.firebase.database.DatabaseReference

class CheckoutViewModel(
    val database: DatabaseReference, val listener: ViewModelListener
) : ObservableViewModel() {
    companion object {
        const val OPEN_CHECKOUT = "open_checkout"
    }
    private val mutableListData: MutableLiveData<List<Menu>> = MutableLiveData()
    val listData: LiveData<List<Menu>> = mutableListData

    @Bindable
    var item = Menu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    fun openCheckout() {
        listener.navigateTo(CheckoutViewModel.OPEN_CHECKOUT)
    }

}