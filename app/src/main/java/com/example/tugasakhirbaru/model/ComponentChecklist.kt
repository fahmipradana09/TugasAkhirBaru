package com.example.tugasakhirbaru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComponentChecklist(
    var menu: String = "",
    var Kkal: Double = 0.0,
    var id: String = "",
    var isChecked: Boolean = false,
    var isMandatory: Boolean = true,
) : Parcelable {
    fun getText() = "$menu ($Kkal Kkal)"

    fun getKkalInString() = "$Kkal kkal"

    constructor(data: Component, isChecked: Boolean, isMandatory: Boolean) : this(
        data.menu, data.Kkal, data.id, isChecked, isMandatory
    )
}