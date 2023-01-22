package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.adapter.MenuAdapter
import com.example.tugasakhirbaru.databinding.ActivityCheckoutBinding
import com.example.tugasakhirbaru.databinding.ActivityHomeBinding
import com.example.tugasakhirbaru.util.KotlinExt.openLoginActivity
import com.example.tugasakhirbaru.util.KotlinExt.openProfileActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CheckoutActivity : AppCompatActivity(), ViewModelListener {
    lateinit var binding: ActivityCheckoutBinding
    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("menus")
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val viewModel: HomeViewModel by lazy {
        HomeViewModel(database, this)
    }

    private val adapter: MenuAdapter by lazy {
        MenuAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.listData.observe(this) { listData ->
            adapter.setData(listData)
        }
        viewModel.getMenuData()
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        TODO("Not yet implemented")
    }
}