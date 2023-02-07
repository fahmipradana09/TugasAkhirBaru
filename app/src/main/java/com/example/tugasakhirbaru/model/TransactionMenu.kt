package com.example.tugasakhirbaru.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionMenu(
    var username: String = "",
    var date: String = "",
    var orderList: HashMap <String, Cart> = hashMapOf(),
    var alamat: String = "",
    var status : String = "prepared",
    var quantity: Int = 1,
) : Parcelable{

    fun totalPrice(): Long {
        var total = 0L
        for ((_, item) in orderList) {
            total += item.totalPrice()
        }
        return total
    }

    @Exclude
    fun quantityToString ()= quantity.toString()

}
