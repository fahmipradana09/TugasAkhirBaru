package com.example.tugasakhirbaru.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.*
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DetailMenuViewModel(
    private val auth: FirebaseAuth,
    private val menuDatabase: DatabaseReference,
    private val cartDatabase: DatabaseReference,
    private val listener: ViewModelListener,
) : ObservableViewModel() {
    companion object {
        const val OPEN_EDIT = "open_edit"
        const val OPEN_CHECKOUT = "open_checkout"
    }

    private val mutableDataMenu: MutableLiveData<Menu> = MutableLiveData()
    val dataMenu: LiveData<Menu> = mutableDataMenu

    @Bindable
    var item = Menu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    fun openEdit() {
        listener.navigateTo(OPEN_EDIT)
    }

    fun plus() {
        item.plus()
        notifyPropertyChanged(BR.item)
    }

    fun minus() {
        item.minus()
        notifyPropertyChanged(BR.item)
    }

    fun getIngredient() {
        menuDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val componentList = arrayListOf<Component>()
                for (children in snapshot.children) {
                    children.getValue(Component::class.java)?.let { component ->
                        componentList.add(component)
                        if (item.ingredient.contains(component.id)) {
                            val index = item.ingredient.indexOf(component.id)
                            item.detailIngredient.add(
                                ComponentChecklist(
                                    component, item.default[index], item.mandatory[index]
                                )
                            )
                        }
                    }
                }
                notifyPropertyChanged(BR.item)
                mutableDataMenu.postValue(item)
            }

            override fun onCancelled(error: DatabaseError) {
                listener.showMessage(error.message)
            }
        })
    }

    fun updateCart() {
        val uid = auth.currentUser?.uid ?: return
        //Data orderlist
        cartDatabase.child(uid).child(DatabasePath.ORDER_LIST).child(item.id).setValue(item)
        listener.navigateTo(OPEN_CHECKOUT)

    }



}