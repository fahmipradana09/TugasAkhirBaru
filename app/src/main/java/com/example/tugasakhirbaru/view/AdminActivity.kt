package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.adapter.AdminAdapter
import com.example.tugasakhirbaru.databinding.ActivityAdminDashboardBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.KotlinExt.openLoginActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.viewmodel.AdminViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdminActivity : AppCompatActivity(), ViewModelListener, AdminAdapter.Listener {
    lateinit var binding: ActivityAdminDashboardBinding

    private val viewModel: AdminViewModel by lazy {
        AdminViewModel(
            FirebaseAuth.getInstance(),
            FirebaseDatabase.getInstance().getReference(DatabasePath.TRANSACTION),
            this
        )
    }

    private val adapter: AdminAdapter by lazy {
        AdminAdapter(this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupMenu()
        binding.viewModel = viewModel
        binding.orderList.adapter = adapter
        binding.orderList.layoutManager = LinearLayoutManager(this)
        viewModel.listData.observe(this){ list ->
            adapter.setData(list)
        }
        
        viewModel.getOrder()
    }

    private fun setupMenu() {
        val popupMenu = PopupMenu(this, binding.toolbar.menuButton)
        popupMenu.menuInflater.inflate(R.menu.option_menu, popupMenu.menu)
        val item = popupMenu.menu.findItem(R.id.profileMenu)
        item.isVisible = false
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    viewModel.auth.signOut()
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

    override fun showMessage(message: String?, isLong: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateTo(param: String) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: Menu) {
        TODO("Not yet implemented")
    }

    override fun removeItem(id: String) {
        TODO("Not yet implemented")
    }
}