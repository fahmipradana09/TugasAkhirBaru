package com.example.tugasakhirbaru.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.model.Component
import com.example.tugasakhirbaru.model.ComponentChecklist
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.ObservableViewModel
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeViewModel(
    val databaseMenu: DatabaseReference,
    val auth: FirebaseAuth,
    val listener: ViewModelListener
) : ObservableViewModel() {
    private val mutableListData: MutableLiveData<List<Menu>> = MutableLiveData()
    val listData: LiveData<List<Menu>> = mutableListData

    private val mutableListHorizontal: MutableLiveData<List<ComponentChecklist>> = MutableLiveData()
    val listHorizontalData: LiveData<List<ComponentChecklist>> = mutableListHorizontal

    @Bindable
    var item = Menu()
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    fun getMenuData() {
        databaseMenu.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listData = arrayListOf<Menu>()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val menu = userSnapshot.getValue(Menu::class.java)
                        if (menu != null) {
                            listData.add(menu)
                        }
                    }
                }
                mutableListData.postValue(listData)
            }

            override fun onCancelled(error: DatabaseError) {
                mutableListData.postValue(listOf())
            }
        })

    }

   /* fun listOrderMenu(){
        val transactions = mutableListOf<Transaction>()
        databaseMenu.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (menu in dataSnapshot.children) {
                val transaction = menu.getValue(Transaction::class.java)
                if (transaction != null) {
                    transactions.add(transaction)
                }
            }

            val doneTransactions = transactions.filter { it.status == "done" }

            doneTransactions.sortBy { it.date }

            val oldestTransaction = doneTransactions.first()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("Transaction", "Failed to read value.", error.toException())
        }
    })
    }*/

    fun getHorzontalData() {
        databaseMenu.orderByValue().addValueEventListener(object : ValueEventListener {
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

            }

            override fun onCancelled(error: DatabaseError) {
                listener.showMessage(error.message)
            }
        })
    }

}