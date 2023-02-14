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
    fun ingredientInString() = ingredient.joinToString(separator = ", ")

    @Exclude
    fun detailIngredientInString() = detailIngredient.joinToString(separator = ", ", transform = {
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

    fun totalFat(): Double {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.fat
            }
        }
        total *= quantity
        return total
    }

    fun totalProtein(): Double {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.protein
            }
        }
        total *= quantity
        return total
    }

    fun totalCarbo(): Double {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.carbo
            }
        }
        total *= quantity
        return total
    }

    @Exclude
    fun toHashMap(totalCarbo: Double, totalProtein: Double, totalFat : Double, totalColories :Double): HashMap<String, Any?> = hashMapOf(
        "totalCarbo" to totalCarbo,
        "totalProtein" to totalProtein,
        "totalFat" to totalFat,
        "totalCalories" to totalColories
    )

    @Exclude
    fun totalCaloriesInString(): String {
        val total = totalCalories()
        return String.format("%.2f kkal", total)
    }

    @Exclude
    fun totalFatInString(): String {
        val total = totalFat()
        return String.format("%.2f g", total)
    }

    @Exclude
    fun totalProteinInString(): String {
        val total = totalProtein()
        return String.format("%.2f g", total)
    }

    @Exclude
    fun totalCarboInString(): String {
        val total = totalCarbo()
        return String.format("%.2f g", total)
    }
}