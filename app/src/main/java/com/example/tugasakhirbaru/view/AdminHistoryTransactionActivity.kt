package com.example.tugasakhirbaru.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.adapter.AdminTransactionAdapter
import com.example.tugasakhirbaru.adapter.HistoryPurchaseAdapter
import com.example.tugasakhirbaru.databinding.ActivityAdminHistoryTransactionBinding
import com.example.tugasakhirbaru.databinding.ActivityPurchasehistoryBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.viewmodel.AdminTransactionHistoryViewModel
import com.example.tugasakhirbaru.viewmodel.PurchaseHistoryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdminHistoryTransactionActivity : AppCompatActivity(),ViewModelListener, AdminTransactionAdapter.Listener {
    lateinit var binding: ActivityAdminHistoryTransactionBinding

    private val viewModel: AdminTransactionHistoryViewModel by lazy {
        AdminTransactionHistoryViewModel(
            FirebaseDatabase.getInstance().getReference(DatabasePath.TRANSACTION),
            FirebaseAuth.getInstance()
        )
    }

    private val adapter: AdminTransactionAdapter by lazy {
        AdminTransactionAdapter(this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHistoryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listItem.adapter = adapter
        binding.listItem.layoutManager = LinearLayoutManager(this)

        viewModel.listData.observe(this) { list ->
            adapter.setData(list)
        }


        viewModel.getMenuData()
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
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