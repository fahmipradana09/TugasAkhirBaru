package com.example.tugasakhirbaru.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
class MenuCart(
    var id: String = "",
    var menu: String = "",
    var picture: String = "",
    var quantity: Int = 1,
    var price: Long = 0,
    val ingredients: ArrayList<ComponentChecklist> = arrayListOf(),
) : Parcelable {
    @Exclude
    fun priceInRupiah() = "Rp ${totalPrice()}"

    @Exclude
    fun plus() = quantity++

    @Exclude
    fun minus() {
        if (quantity > 1) {
            quantity--
        }
    }

    @Exclude
    fun quantityInString() = quantity.toString()

    @Exclude
    fun imageExists() = picture.isNotBlank()

    @Exclude
    fun totalCalories(): Double {
        var total = 0.0
        for (component in ingredients) {
            if (component.isChecked) {
                total += component.Kkal
            }
        }
        total *= quantity
        return total
    }

    @Exclude
    fun totalCaloriesInString(): String = "${totalCalories()} Kkal"

    @Exclude
    fun totalPrice() = price * quantity

    @Exclude
    fun ingredientInString() = ingredients.joinToString(separator = ", ", transform = {
        it.menu
    })
}