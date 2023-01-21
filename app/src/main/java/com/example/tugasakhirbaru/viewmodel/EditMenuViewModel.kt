package com.example.tugasakhirbaru.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Component
import com.example.tugasakhirbaru.model.ComponentChecklist
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class EditMenuViewModel(
    val database: DatabaseReference, val listener: ViewModelListener
) : ObservableViewModel() {
    companion object {
        const val OPEN_EDIT = "open_edit"
    }

    @Bindable
    var item = Menu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    @Bindable
    var isLoading = false

    fun openEdit() {
        listener.navigateTo(OPEN_EDIT)
    }

    fun getIngredient() {
        // isLoading = true

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val componentList = arrayListOf<Component>()
                for (children in snapshot.children) {
                    children.getValue(Component::class.java)?.let { component ->
                        componentList.add(component)
                        if (item.hashMap.containsKey(component.id)) {
                            item.detailIngredient.add(
                                ComponentChecklist(component, item.hashMap.getValue(component.id))
                            )
                        }
                        /*if (item.ingredient.contains(component.id)) {
                            item.detailIngredient.add(component)
                        }*/
                    }
                }
                notifyPropertyChanged(BR.item)
                Log.d("DetailActivity", "Item data: $item")

                // Log.d("DetailViewModel", "Component list: $componentList")
            }

            override fun onCancelled(error: DatabaseError) {
                listener.showMessage(error.message)
            }
        })

    }
}