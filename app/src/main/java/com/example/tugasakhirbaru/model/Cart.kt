package com.example.tugasakhirbaru.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart (
    var userId: String = "",
    var address: String = "",
    val orderList: HashMap<String, MenuCart> = hashMapOf()
) : Parcelable {
    @Exclude
    fun toHashMap(): HashMap<String, Any?> = hashMapOf(
        "userId" to userId,
        "address" to address
    )

    @Exclude
    fun totalCalories(): String {
        var total = 0.0
        for ((_, item) in orderList) {
            total += item.totalCalories()
        }
        return "$total Kkal"
    }

    @Exclude
    fun totalPrice(): String {
        var total = 0L
        for ((_, item) in orderList) {
            Log.d("Cart", "Item ${item.id}: ${item.totalPrice()}" )
            total += item.totalPrice()
        }
        return "Rp $total"
    }
}