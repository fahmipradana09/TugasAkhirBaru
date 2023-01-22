package com.example.tugasakhirbaru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var description: String = "",
    var menu: String = "",
    var picture: String = "",
    var price: String = "",
//    val ingredient: List<String> = arrayListOf(),
//    val default: List<Boolean> = arrayListOf(),
    var quantity: Int = 1,
    val hashMap: HashMap<String, Boolean> = hashMapOf(),
    val detailIngredient: ArrayList<ComponentChecklist> = arrayListOf(),
) : Parcelable {
    fun priceToString() = price

    fun priceInRupiah() = "Rp ${priceToString()}"

    fun imageExists() = picture.isNotBlank()

    fun ingredientInString() = detailIngredient.joinToString(separator = ", ", transform = {
        it.menu
    })

    fun plus() = quantity++

    fun minus() = quantity--

    fun quantityInString() = quantity.toString()

    fun totalPrice() = price.toDouble() * quantity

    fun totalCalories(): String {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.Kkal
            }
        }
        total *= quantity
        return "$total Kkal"
    }
}