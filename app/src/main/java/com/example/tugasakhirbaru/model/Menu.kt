package com.example.tugasakhirbaru.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var description: String = "",
    var id: String = "",
    var menu: String = "",
    var picture: String = "",
    var price: Long = 0,
    val ingredient: List<String> = arrayListOf(),
    val default: List<Boolean> = arrayListOf(),
    val mandatory: List<Boolean> = arrayListOf(),
    var quantity: Int = 1,
    val detailIngredient: ArrayList<ComponentChecklist> = arrayListOf(),
) : Parcelable {
    @Exclude
    fun priceInRupiah() = "Rp ${totalPrice()}"

    @Exclude
    fun imageExists() = picture.isNotBlank()

    @Exclude
    fun ingredientInString() = detailIngredient.joinToString(separator = ", ", transform = {
        it.menu
    })

    @Exclude
    fun plus() = if (quantity > 100) null else quantity++

    @Exclude
    fun minus() = if (quantity <= 1) null else quantity--

    @Exclude
    fun quantityInString() = quantity.toString()

    @Exclude
    fun totalPrice() = price * quantity

    @Exclude
    fun totalCalories(): Double {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.Kkal
            }
        }
        total *= quantity
        return total
    }

    @Exclude
    fun totalCaloriesInString(): String = "${totalCalories()} Kkal"
}