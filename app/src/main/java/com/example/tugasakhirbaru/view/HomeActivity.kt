package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.adapter.MenuAdapter
import com.example.tugasakhirbaru.databinding.ActivityHomeBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.KotlinExt.openLoginActivity
import com.example.tugasakhirbaru.util.KotlinExt.openProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("menus")
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
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

        getMenuData()
        showMore()


    }

    private fun getMenuData() {
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

    private fun showMore() {
        val popupMenu = PopupMenu(this, binding.toolbar.menuButton)
        popupMenu.menuInflater.inflate(R.menu.option_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.profile -> {
                    openProfileActivity()
                    true
                }
                R.id.logout ->{
                    auth.signOut()
                    finish()
                    openLoginActivity()
                    true
                }
                else -> false
            }

        }
        binding.toolbar.menuButton.setOnClickListener {
            popupMenu.show()
        }

    }
}