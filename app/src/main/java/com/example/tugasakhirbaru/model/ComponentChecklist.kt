package com.example.tugasakhirbaru.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComponentChecklist(
    var menu: String = "",
    var Kkal: Double = 0.0,
    var carbo: Double = 0.0,
    var fat: Double = 0.0,
    var protein: Double = 0.0,
    var id: String = "",
    var isChecked: Boolean = false,
    var isMandatory: Boolean = true,
) : Parcelable {

    @Exclude
    fun getText() = "$menu ($Kkal Kkal)"

    fun getKkalInString() = "$Kkal kkal"

    constructor(data: Component, isChecked: Boolean, isMandatory: Boolean) : this(
        data.menu, data.Kkal, data.carbo, data.fat, data.protein, data.id, isChecked, isMandatory
    )
}