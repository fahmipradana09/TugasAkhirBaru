package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class EditMenuViewModel(
    private val auth: FirebaseAuth,
    private val menuDatabase: DatabaseReference,
    private val cartDatabase: DatabaseReference,
    private val listener: ViewModelListener
) : ObservableViewModel() {
    companion object {
        const val OPEN_CHECKOUT = "open_checkout"
    }

    private val mutableData: MutableLiveData<Menu> = MutableLiveData()
    val data: LiveData<Menu> = mutableData

    @Bindable
    var item = Menu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }


    fun plus() {
        item.plus()
        notifyPropertyChanged(BR.item)
    }

    fun minus() {
        item.minus()
        notifyPropertyChanged(BR.item)
    }


    fun updateCart() {
        val uid = auth.currentUser?.uid ?: return
        val dataWithPrefix = item.id + Constants.PREFIX_EDIT
        val data: HashMap<String, Any> = HashMap()
        data["id"] = item.id + Constants.PREFIX_EDIT
        data["menu"] = item.menu + Constants.PREFIX_EDIT

        cartDatabase.child(uid).child(DatabasePath.ORDER_LIST).child(dataWithPrefix).setValue(item)
        cartDatabase.child(uid).child(DatabasePath.ORDER_LIST).child(dataWithPrefix)
            .updateChildren(data)
        notifyPropertyChanged(BR.item)
        mutableData.postValue(item)

        listener.navigateTo(DetailMenuViewModel.OPEN_CHECKOUT)
    }
}