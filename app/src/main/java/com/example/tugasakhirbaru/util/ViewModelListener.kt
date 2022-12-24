package com.example.tugasakhirbaru.util

interface ViewModelListener {
    fun showMessage(message: String?, isLong: Boolean = false)
    fun navigateTo(param: String)
}