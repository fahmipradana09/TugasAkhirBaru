package com.example.tugasakhirbaru.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Component
import com.example.tugasakhirbaru.model.ComponentChecklist
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.repository.UserPreference
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class EditMenuViewModel(
    private val auth: FirebaseAuth,
    private val menuDatabase: DatabaseReference,
    private val cartDatabase: DatabaseReference,
    private val listener: ViewModelListener
) : ObservableViewModel() {
    companion object {
        const val OPEN_CHECKOUT = "open_checkout"
    }

    private val mutableData: MutableLiveData<Menu> = MutableLiveData()
    val data: LiveData<Menu> = mutableData
    @Bindable
    var item = Menu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }


    fun plus() {
        item.plus()
        notifyPropertyChanged(BR.item)
    }

    fun minus() {
        item.minus()
        notifyPropertyChanged(BR.item)
    }

    fun getIngredient() {
        menuDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val componentList = arrayListOf<Component>()
                for (children in snapshot.children) {
                    children.getValue(Component::class.java)?.let { component ->
                        componentList.add(component)
                        if (item.ingredient.contains(component.id)) {
                            val index = item.ingredient.indexOf(component.id)
                            item.detailIngredient.add(
                                ComponentChecklist(
                                    component, item.default[index], item.mandatory[index]
                                )
                            )

                        }
                    }
                }
                notifyPropertyChanged(BR.item)
                mutableData.postValue(item)
            }

            override fun onCancelled(error: DatabaseError) {
                listener.showMessage(error.message)
            }
        })
    }


    fun updateCart() {
        val uid = auth.currentUser?.uid ?: return
        val dataWithPrefix = item.id + Constants.PREFIX_EDIT
        val data: HashMap<String, Any> = HashMap()
        data["id"] = item.id+Constants.PREFIX_EDIT
        data["menu"] = item.menu+Constants.PREFIX_EDIT

        cartDatabase.child(uid).child(DatabasePath.ORDER_LIST).child(dataWithPrefix).setValue(item)
        cartDatabase.child(uid).child(DatabasePath.ORDER_LIST).child(dataWithPrefix).updateChildren(data)
        notifyPropertyChanged(BR.item)
        mutableData.postValue(item)

        listener.navigateTo(DetailMenuViewModel.OPEN_CHECKOUT)
    }
}