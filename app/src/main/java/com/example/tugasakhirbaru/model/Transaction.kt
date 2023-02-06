package com.example.tugasakhirbaru.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    var id: Long  = 1L,
    var username: String = "",
    var timestamp: String = "",
    var menu: HashMap <String, Cart> = hashMapOf(),
    var alamat: String = "",
    var totalprice: Long = 0L

) : Parcelable{
    @Exclude
    fun getCurrentId():Long{
        val currectId = 0
        if (id > 1 ){
            id += currectId
        }
        return id
    }

    fun totalPrice(): Long {
        var total = 0L
        for ((_, item) in menu) {
            total += item.totalPrice()
        }
        return total
    }

}
