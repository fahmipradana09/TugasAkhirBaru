package com.example.tugasakhirbaru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    var id: String  ="",
    var username: String = "",
    var timestamp: String = "",
    var menu: String = "",
    var alamat: String = "",
    var totalprice: Double = 0.0,

) : Parcelable{

}
