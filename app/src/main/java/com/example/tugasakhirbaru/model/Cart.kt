package com.example.tugasakhirbaru.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart (
    var user : String = "",
    var address: String = "",
    var detailIngredient: ArrayList<ComponentChecklist> = arrayListOf(),
    val orderList: HashMap<String, Menu> = hashMapOf()
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

    fun totalToString() = "Rp ${totalPrice()}"
}