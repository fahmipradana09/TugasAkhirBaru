package com.example.tugasakhirbaru.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Cart
import com.example.tugasakhirbaru.model.MenuCart
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class CheckoutViewModel(
    private val auth: FirebaseAuth,
    private val database: DatabaseReference,
    private val listener: ViewModelListener
) : ObservableViewModel() {

    private val mutableListData: MutableLiveData<List<MenuCart>> = MutableLiveData()
    val listData: LiveData<List<MenuCart>> = mutableListData

    @Bindable
    var cart = Cart()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    fun getUserCart() {
        val uid = auth.currentUser?.uid ?: return

        // Transaction with new ID examples
        /*val ref = database.child(uid)
        val id = ref.push().key ?: ""
        item.id = id
        database.child(uid).child(id).*/

        database.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cart = snapshot.getValue(Cart::class.java)
                /*val menuList = arrayListOf<MenuCart>()
                if (snapshot.exists()) {
                    for (menuSnapshot in snapshot.children) {
                        val menu = menuSnapshot.getValue(MenuCart::class.java)
                        if (menu != null) {
                            menuList.add(menu)
                        }
                    }
                }*/
                if (cart != null) {
                    this@CheckoutViewModel.cart = cart
                    mutableListData.postValue(cart.orderList.values.toList())
                    notifyPropertyChanged(BR.cart)
                } else {
                    mutableListData.postValue(listOf())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mutableListData.postValue(listOf())
            }
        })

        /*database.child(uid).child(DatabasePath.ORDER_LIST).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val menuList = arrayListOf<MenuCart>()
                if (snapshot.exists()) {
                    for (menuSnapshot in snapshot.children) {
                        val menu = menuSnapshot.getValue(MenuCart::class.java)
                        if (menu != null) {
                            menuList.add(menu)
                        }
                    }
                }
                mutableListData.postValue(menuList)
            }

            override fun onCancelled(error: DatabaseError) {
                mutableListData.postValue(listOf())
            }
        })*/
    }

    fun updateCart() {
        val uid = auth.currentUser?.uid ?: return
        cart.userId = uid

        database.child(uid).updateChildren(cart.toHashMap())
    }

    fun updateItem(item: MenuCart) {
        val uid = auth.currentUser?.uid ?: return

        cart.orderList[item.id] = item
        notifyPropertyChanged(BR.cart)

        cart.totalPrice()

        database.child(uid).child(DatabasePath.ORDER_LIST).child(item.id).setValue(item)
    }

    fun removeItem(id: String) {
        val uid = auth.currentUser?.uid ?: return

        cart.orderList.remove(id)
        notifyPropertyChanged(BR.cart)

        database.child(uid).child(DatabasePath.ORDER_LIST).child(id).setValue(null)
    }
}