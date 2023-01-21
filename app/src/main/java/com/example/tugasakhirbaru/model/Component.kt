package com.example.tugasakhirbaru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Component(
    var menu: String = "",
    var Kkal: Double = 0.0,
    var id: String = ""
) : Parcelable {
    val stringKkal = Kkal.toString()
}