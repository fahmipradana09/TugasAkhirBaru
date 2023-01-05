package com.example.tugasakhirbaru.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.tugasakhirbaru.adapter.MenuAdapter
import com.example.tugasakhirbaru.databinding.ActivityDetailBinding
import com.example.tugasakhirbaru.databinding.ActivityHomeBinding
import com.example.tugasakhirbaru.model.Menu
import com.google.firebase.database.*

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    lateinit var menuRecyclerView : RecyclerView
    lateinit var menuArrayList: ArrayList<Menu>

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("menus")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)



    }

}