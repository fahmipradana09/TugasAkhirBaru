package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasakhirbaru.adapter.MenuAdapter
import com.example.tugasakhirbaru.databinding.ActivityHomeBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.RecyclerViewClickListener
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity(), RecyclerViewClickListener {
    lateinit var binding: ActivityHomeBinding
    lateinit var menuRecyclerView: RecyclerView
    lateinit var menuArrayList: ArrayList<Menu>


    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("menus")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        menuArrayList = arrayListOf<Menu>()
        getUserData()

        val menuAdapter = MenuAdapter(menuArrayList)
        menuAdapter.listener = this

        menuRecyclerView = binding.menuList

            menuRecyclerView.adapter = menuAdapter
            menuRecyclerView.layoutManager = GridLayoutManager(this@HomeActivity, 2)
            menuRecyclerView.setHasFixedSize(true)



    }

    private fun getUserData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val menu = userSnapshot.getValue(Menu::class.java)
                        menuArrayList.add(menu!!)
                    }
                    menuRecyclerView.adapter = MenuAdapter(menuArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onItemClicked(view: View, menu: Menu) {
        Toast.makeText(
            this, "menu berhasil di klik", Toast.LENGTH_SHORT
        ).show()
    }
}