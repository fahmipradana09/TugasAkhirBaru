package com.example.tugasakhirbaru.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart (
    var statusMakanan : String = "Prepared",
    var statusPembayaran : String = "Pending",
    val orderList: HashMap<String, Menu> = hashMapOf(),
    val totalPrice :Long =0L,
    val quantity :Int = 0,
) : Parcelable {
    fun totalPrice(): Long {
        var total = 0L
        for ((_, item) in orderList) {
            total += item.totalPrice()
        }
        return total
    }

    fun totalProtein(): Double {
        var total = 0.0
        for ((_,item) in orderList){
            total += item.totalProtein()
        }
        return total
    }

    fun totalCarbo(): Double {
        var total = 0.0
        for ((_,item) in orderList){
            total += item.totalCarbo()
        }
        return total
    }

     fun totalFat(): Double {
            var total = 0.0
            for ((_,item) in orderList){
                total += item.totalFat()
            }
            return total
     }

    fun totalCalories(): Double {
        var total = 0.0
        for ((_,item) in orderList){
            total += item.totalCalories()
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

    @Exclude
    fun totalCaloriesInString(): String {
        return String.format("%.2f kkal", totalCalories())
    }

    @Exclude
    fun totalFatInString(): String {
        return String.format("%.2f g", totalFat())
    }

    @Exclude
    fun totalProteinInString(): String {
        return String.format("%.2f g", totalProtein())
    }

    @Exclude
    fun totalCarboInString(): String {
        return String.format("%.2f g", totalCarbo())
    }
}