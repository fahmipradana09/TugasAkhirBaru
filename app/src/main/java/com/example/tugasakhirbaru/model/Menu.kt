package com.example.tugasakhirbaru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var description: String = "",
    var menu: String = "",
    var picture: String = "",
    var price: String = ""
) : Parcelable {
    fun imageExists() = picture.isNotBlank()
}