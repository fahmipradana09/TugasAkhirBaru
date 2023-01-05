package com.example.tugasakhirbaru.util

import android.view.View
import com.example.tugasakhirbaru.model.Menu


interface RecyclerViewClickListener {
    fun onItemClicked(view: View, menu: Menu)
}