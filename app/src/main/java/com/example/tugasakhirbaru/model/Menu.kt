package com.example.tugasakhirbaru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var description: String = "",
    var menu: String = "",
    var picture: String = "",
    var price: Long = 0,
    val ingredient: List<String> = arrayListOf(),
    val default: List<Boolean> = arrayListOf(),
    val mandatory: List<Boolean> = arrayListOf(),
    var quantity: Int = 1,
    val detailIngredient: ArrayList<ComponentChecklist> = arrayListOf(),
) : Parcelable {
    fun priceTotal() = price + (quantity-1)* price

    fun priceInRupiah() = "Rp ${priceTotal()}"

    fun imageExists() = picture.isNotBlank()

    fun ingredientInString() = detailIngredient.joinToString(separator = ", ", transform = {
        it.menu
    })

    fun plus() = quantity++

    fun minus() {
        if (quantity > 1)
        quantity--
    }

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