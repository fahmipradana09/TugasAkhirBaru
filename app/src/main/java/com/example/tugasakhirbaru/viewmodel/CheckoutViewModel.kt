package com.example.tugasakhirbaru.viewmodel

import android.app.Activity
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.*
import com.example.tugasakhirbaru.repository.UserPreference
import com.example.tugasakhirbaru.util.Dialog
import com.example.tugasakhirbaru.util.KotlinExt.convertToFormat
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants
import com.example.tugasakhirbaru.util.constants.Constants.OPEN_HOME
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class CheckoutViewModel(
    private val auth: FirebaseAuth,
    private val databaseCart: DatabaseReference,
    private val databaseTransaction: DatabaseReference,
    val userPreference: UserPreference,
    private val listener: ViewModelListener
) : ObservableViewModel() {

    private val mutableListData: MutableLiveData<List<Menu>> = MutableLiveData()
    val listData: LiveData<List<Menu>> = mutableListData

    @Bindable
    var cart = Cart()
        set(value) {
            field = value
            notifyPropertyChanged(BR.cart)
        }

    @Bindable
    var item = Menu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    fun openHome(){
        listener.navigateTo(OPEN_HOME)
    }

    fun getUserCart() {
        val uid = auth.currentUser?.uid ?: return
        databaseCart.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartData = snapshot.getValue(Cart::class.java)
                if (cartData != null) {
                    cart = cartData
                    mutableListData.postValue(cartData.orderList.values.toList())
                    notifyPropertyChanged(BR.cart)
                } else {
                    mutableListData.postValue(listOf())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mutableListData.postValue(listOf())
            }
        })
    }


    fun transaction() {
        val timestamp = Calendar.getInstance().convertToFormat(Constants.TIMESTAMP_FORMAT)
        val userData = userPreference.getUser()
        val id = databaseTransaction.push().key ?: ""
        val dbTransaction = databaseTransaction.child(id)
        val additionData = mutableMapOf<String, Any>()

        additionData["id"] = id
        additionData["date"] = timestamp
        additionData["totalPrice"] = cart.totalPrice()
        additionData["totalProtein"] = cart.totalProtein()
        additionData["totalCarbo"] = cart.totalCarbo()
        additionData["totalFat"] = cart.totalFat()
        additionData["totalCalories"] = cart.totalCalories()
        additionData["quantity"] = cart.totalQuantity()
        additionData["uid"] = auth.currentUser!!.uid
        dbTransaction.setValue(cart
        ) { error, _ ->
            if (error == null) {
                dbTransaction.updateChildren(
                    userData.toHashMap(
                        userData.alamat,
                        userData.username,
                        userData.phone
                    )
                )
                dbTransaction.updateChildren(additionData)
            }
        }
    }

    fun updateItem(item: Menu) {
        val uid = auth.currentUser?.uid ?: return

        cart.orderList[item.id] = item
        notifyPropertyChanged(BR.cart)

        cart.totalPrice()

        databaseCart.child(uid).child(DatabasePath.ORDER_LIST).child(item.id).setValue(item)
    }

    fun removeItem(id: String) {
        val uid = auth.currentUser?.uid ?: return

        cart.orderList.remove(id)
        notifyPropertyChanged(BR.cart)

        databaseCart.child(uid).child(DatabasePath.ORDER_LIST).child(id).setValue(null)
    }

}