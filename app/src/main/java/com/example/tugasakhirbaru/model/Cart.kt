package com.example.tugasakhirbaru.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart (
    var status : String = "prepared",
    val orderList: HashMap<String, Menu> = hashMapOf(),
    val totalPrice :Long =0L,
    val quantity :Int = 0
) : Parcelable {
    @Exclude
    fun totalCalories(): String {
        var total = 0.0
        for ((_, item) in orderList) {
            total += item.totalCalories()
        }
        return "$total Kkal"
    }

    fun totalPrice(): Long {
        var total = 0L
        for ((_, item) in orderList) {
            total += item.totalPrice()
        }
        return total
    }

    fun totalQuantity(): Int {
        var total = 0
        for((_,item) in orderList){
            total += item.quantity
        }
        return total
    }


    @Exclude
    fun totalToString() = "Rp ${totalPrice()}"
}