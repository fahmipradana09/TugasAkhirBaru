package com.example.tugasakhirbaru.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugasakhirbaru.adapter.MenuAdapter
import com.example.tugasakhirbaru.databinding.ActivityHomeBinding
import com.example.tugasakhirbaru.model.Menu
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("menus")
    }

    private val adapter: MenuAdapter by lazy {
        MenuAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.menuList.adapter = adapter
        binding.menuList.layoutManager = GridLayoutManager(this@HomeActivity, 2)

        getUserData()
    }

    private fun getUserData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val listData = arrayListOf<Menu>()
                    for (userSnapshot in snapshot.children) {
                        val menu = userSnapshot.getValue(Menu::class.java)
                        if (menu != null) {
                            listData.add(menu)
                        }
                    }

                    adapter.setData(listData)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}