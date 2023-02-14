package com.example.tugasakhirbaru.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionMenu(
    var username: String = "",
    var uid: String = "",
    var date: String = "",
    var orderList: HashMap <String, Cart> = hashMapOf(),
    var alamat: String = "",
    var status : String = "prepared",
    var quantity: Int = 1,
    var totalPrice: Long = 0L,
    var totalCalories: Double = 0.0,
    var totalCarbo: Double = 0.0,
    var totalFat: Double = 0.0,
    var totalProtein: Double = 0.0
) : Parcelable{

    @Exclude
    fun totalCaloriesInString(): String {
        return String.format("%.2f kkal", totalCalories)
    }

    @Exclude
    fun totalFatInString(): String {
        return String.format("%.2f g", totalFat)
    }

    @Exclude
    fun totalProteinInString(): String {
        return String.format("%.2f g", totalProtein)
    }

    @Exclude
    fun totalCarboInString(): String {
        return String.format("%.2f g", totalCarbo)
    }
    @Exclude
    fun quantityToString ()= quantity.toString()

    @Exclude
    fun totalPriceToString () = "Rp ${totalPrice}"

}
