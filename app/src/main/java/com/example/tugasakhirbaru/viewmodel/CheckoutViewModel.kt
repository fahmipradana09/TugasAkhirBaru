package com.example.tugasakhirbaru.viewmodel

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.*
import com.example.tugasakhirbaru.repository.UserPreference
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants.OPEN_HOME
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.util.*

class CheckoutViewModel(
    private val auth: FirebaseAuth,
    private val databaseCart: DatabaseReference,
    private val databaseTransaction: DatabaseReference,
    private val userPreference: UserPreference,
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
        val timestamp = Calendar.getInstance().time
        val userData = userPreference.getUser()
        val id = databaseTransaction.push().key ?: ""
        val dbTransaction = databaseTransaction.child(id)
        val dateMap = mutableMapOf<String, Any>()

//        if (userData.isDataBlank()){
//           listener.showMessage("Data anda masih kosong")
//            return
//        }

        dateMap["date"] = timestamp.toString()

        dbTransaction.child(DatabasePath.ORDER_LIST).setValue(cart.orderList)
        dbTransaction.updateChildren(userData.toHashMap(userData.alamat,userData.username,userData.phone))
        dbTransaction.updateChildren(dateMap)

        listener.showMessage("Data anda masih belum lengkap")
    }

   /* fun compareData(editedIngredient: List<Map<String, Any>>, defaultIngredient: List<Boolean>): Boolean {
        val uid = auth.currentUser?.uid
        databaseCart.child(uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataEdit = dataSnapshot.child(DatabasePath.DETAIL_INGREDIENT).getValue(object : GenericTypeIndicator<List<Map<String, Any>>>() {})
                val dataDefault = dataSnapshot.child(DatabasePath.DEFAULT).getValue(object : GenericTypeIndicator<List<Boolean>>() {})

                val result = compareData(dataEdit!!, dataDefault!!)
                Log.i ("tes", "ini result : $result")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return editedIngredient.zip(defaultIngredient)
            .all { (map, bool) -> map["checked"] as Boolean == bool }
    }*/


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