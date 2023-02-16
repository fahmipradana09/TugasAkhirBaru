package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.adapter.HorizontalMenuAdapter
import com.example.tugasakhirbaru.adapter.MenuAdapter
import com.example.tugasakhirbaru.databinding.ActivityHomeBinding
import com.example.tugasakhirbaru.util.KotlinExt.openCheckoutActivity
import com.example.tugasakhirbaru.util.KotlinExt.openHistory
import com.example.tugasakhirbaru.util.KotlinExt.openLoginActivity
import com.example.tugasakhirbaru.util.KotlinExt.openProfileActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity(), ViewModelListener {
    lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by lazy {
        HomeViewModel(
            FirebaseDatabase.getInstance().getReference(DatabasePath.MENU),
            FirebaseDatabase.getInstance().getReference(DatabasePath.COMPONENT),
            FirebaseAuth.getInstance(),
            this,
        )
    }

    private val adapterMenu: MenuAdapter by lazy {
        MenuAdapter(this)
    }

    private val adapterMenuHorizontal: HorizontalMenuAdapter by lazy {
        HorizontalMenuAdapter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupMenu()

        binding.toolbar.cartButton.setOnClickListener {
            openCheckoutActivity()
        }

        binding.toolbar.orderHistoryButton.setOnClickListener {
            openHistory()
        }

        binding.viewModel = viewModel

        binding.menuList.adapter = adapterMenu
        binding.rvHorizontalList.adapter = adapterMenuHorizontal

        binding.rvHorizontalList.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL, false)
        binding.menuList.layoutManager = GridLayoutManager(this,2)

        viewModel.listHorizontalData.observe(this){ data ->
            adapterMenuHorizontal.setData(data)
        }
        viewModel.listData.observe(this) { listData ->
            adapterMenu.setData(listData)
        }

        viewModel.getMenuData()
        viewModel.getHorzontalData()
    }

    private fun setupMenu() {
        val popupMenu = PopupMenu(this, binding.toolbar.menuButton)
        popupMenu.menuInflater.inflate(R.menu.option_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.profileMenu -> {
                    openProfileActivity()
                    true
                }
                R.id.logout -> {
                    finish()
                    viewModel.auth.signOut()
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

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        TODO("Not yet implemented")
    }
}