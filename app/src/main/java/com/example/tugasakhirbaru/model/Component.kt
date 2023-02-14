package com.example.tugasakhirbaru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Component(
    var menu: String = "",
    var Kkal: Double = 0.0,
    var carbo: Double = 0.0,
    var fat: Double = 0.0,
    var protein: Double = 0.0,
    var id: String = ""
) : Parcelable {

}