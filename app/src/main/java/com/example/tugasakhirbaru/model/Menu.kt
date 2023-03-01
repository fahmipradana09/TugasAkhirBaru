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
    var calorie : String = ""
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
    fun quantityInStringWithX() : String{
        return "x${quantityInString()}"
    }

    @Exclude
    fun totalPrice() = price * quantity

    fun totalCaloriesIngredient(): Double {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.Kkal
            }
        }
        return total
    }

    fun totalFatIngredient(): Double {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.fat
            }
        }
        return total
    }

    fun totalProteinIngredient(): Double {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.protein
            }
        }
        return total
    }

    fun totalCarboIngredient(): Double {
        var total = 0.0
        for (component in detailIngredient) {
            if (component.isChecked) {
                total += component.carbo
            }
        }
        return total
    }

    @Exclude
    fun totalCalories(): Double {
        val total = totalCaloriesIngredient() * quantity
        return total
    }

    @Exclude
    fun totalFat(): Double {
        val total = totalFatIngredient() * quantity
        return total
    }

    @Exclude
    fun totalProtein(): Double {
        val total = totalProteinIngredient() * quantity
        return total
    }

    @Exclude
    fun totalCarbo(): Double {
        val total = totalCarboIngredient() * quantity
        return total
    }

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

    @Exclude
    fun calorieWithKkalMenu(): String{
        return "Calorie : $calorie kkal"
    }


}